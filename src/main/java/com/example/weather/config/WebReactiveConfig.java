package com.example.weather.config;

import com.example.weather.StaticFileView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.reactiveweb.ReactiveWebAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;

@Configuration
public class WebReactiveConfig extends ReactiveWebAutoConfiguration.WebReactiveConfig {
    @Bean
    public UrlBasedViewResolver urlBasedViewResolver(){
        UrlBasedViewResolver urlBasedViewResolver =  new UrlBasedViewResolver();

        urlBasedViewResolver.setViewNames("*");
        urlBasedViewResolver.setViewClass(StaticFileView.class);

        return urlBasedViewResolver;
    }


    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
//        registry.freeMarker().viewNames("*.ftl");
        registry.viewResolver(urlBasedViewResolver());
    }
}
