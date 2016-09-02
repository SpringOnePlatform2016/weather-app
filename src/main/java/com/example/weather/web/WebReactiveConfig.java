package com.example.weather.web;

import org.springframework.boot.autoconfigure.reactiveweb.ReactiveWebAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class WebReactiveConfig extends ReactiveWebAutoConfiguration.WebReactiveConfig {

    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {
        registry.freeMarker().viewNames("summary");
    }

    @Configuration
    public static class FreeMakerConfig extends FreeMarkerConfigurer {

    }
}
