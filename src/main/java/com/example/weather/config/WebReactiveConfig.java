package com.example.weather.config;

import org.springframework.boot.autoconfigure.reactiveweb.ReactiveWebAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.reactive.config.PathMatchConfigurer;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.util.HttpRequestPathHelper;

@Configuration
public class WebReactiveConfig extends ReactiveWebAutoConfiguration.WebReactiveConfig {

    private static final String TEMPLATE_PATH = "classpath:templates/";

    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        registry.freeMarker().viewNames("*");
    }

    @Override
    public void configurePathMatching(PathMatchConfigurer configurer) {
        configurer
                .setPathHelper(new HttpRequestPathHelper())
                .setPathMatcher(new AntPathMatcher());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/", "classpath:/static/");
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();

        configurer.setPreferFileSystemAccess(false);
        configurer.setTemplateLoaderPath(TEMPLATE_PATH);

        return configurer;
    }
}
