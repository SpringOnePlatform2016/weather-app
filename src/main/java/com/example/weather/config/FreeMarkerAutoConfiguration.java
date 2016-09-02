package com.example.weather.config;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.reactiveweb.ReactiveWebAutoConfiguration;
import org.springframework.boot.autoconfigure.template.TemplateLocation;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfig;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * {@link EnableAutoConfiguration Auto-configuration} for FreeMarker.
 *
 * @author Andy Wilkinson
 * @author Dave Syer
 * @author Kazuki Shimizu
 * @since 1.1.0
 */
@Configuration
@ConditionalOnClass({ freemarker.template.Configuration.class,
        FreeMarkerConfigurationFactory.class })
@EnableConfigurationProperties(FreeMarkerProperties.class)
public class FreeMarkerAutoConfiguration {

    private static final Log logger = LogFactory
            .getLog(FreeMarkerAutoConfiguration.class);

    private final ApplicationContext applicationContext;

    private final FreeMarkerProperties properties;

    public FreeMarkerAutoConfiguration(ApplicationContext applicationContext,
                                       FreeMarkerProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    @PostConstruct
    public void checkTemplateLocationExists() {
        if (this.properties.isCheckTemplateLocation()) {
            TemplateLocation templatePathLocation = null;
            List<TemplateLocation> locations = new ArrayList<>();
            for (String templateLoaderPath : this.properties.getTemplateLoaderPath()) {
                TemplateLocation location = new TemplateLocation(templateLoaderPath);
                locations.add(location);
                if (location.exists(this.applicationContext)) {
                    templatePathLocation = location;
                    break;
                }
            }
            if (templatePathLocation == null) {
                logger.warn("Cannot find template location(s): " + locations
                        + " (please add some templates, "
                        + "check your FreeMarker configuration, or set "
                        + "spring.freemarker.checkTemplateLocation=false)");
            }
        }
    }

    protected static class FreeMarkerConfiguration {

        @Autowired
        protected FreeMarkerProperties properties;

        protected void applyProperties(FreeMarkerConfigurationFactory factory) {
            factory.setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
            factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
            factory.setDefaultEncoding(this.properties.getCharsetName());
            Properties settings = new Properties();
            settings.putAll(this.properties.getSettings());
            factory.setFreemarkerSettings(settings);
        }

    }

    @Configuration
    @ConditionalOnClass(ReactiveWebAutoConfiguration.class)
    public static class FreeMarkerWebConfiguration extends FreeMarkerConfiguration {

        @Bean
        @ConditionalOnMissingBean(FreeMarkerConfig.class)
        public FreeMarkerConfigurer freeMarkerConfigurer() {
            FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
            applyProperties(configurer);
            return configurer;
        }

        @Bean
        public freemarker.template.Configuration freeMarkerConfiguration(
                FreeMarkerConfig configurer) {
            return configurer.getConfiguration();
        }

        @Bean
        @ConditionalOnMissingBean(name = "freeMarkerViewResolver")
        @ConditionalOnProperty(name = "spring.freemarker.enabled", matchIfMissing = true)
        public FreeMarkerViewResolver freeMarkerViewResolver() {
            FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
            this.properties.applyToViewResolver(resolver);
            return resolver;
        }
    }
}
