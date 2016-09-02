package com.example.weather.config;

import org.springframework.boot.autoconfigure.reactiveweb.ReactiveWebAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.result.view.HttpMessageWriterView;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class WebReactiveConfig extends ReactiveWebAutoConfiguration.WebReactiveConfig {

    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
        viewResolver.setViewNames("*");
        registry.viewResolver(viewResolver);
//        registry.
    }
}
