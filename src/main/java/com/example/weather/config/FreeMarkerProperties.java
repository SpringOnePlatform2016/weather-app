package com.example.weather.config;

/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.springframework.boot.autoconfigure.template.AbstractTemplateViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerViewResolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link ConfigurationProperties} for configuring FreeMarker.
 *
 * @author Dave Syer
 * @author Andy Wilkinson
 * @since 1.1.0
 */
@ConfigurationProperties(prefix = "spring.freemarker")
public class FreeMarkerProperties extends AbstractTemplateViewResolverProperties {

    public static final String DEFAULT_TEMPLATE_LOADER_PATH = "classpath:/templates/";

    public static final String DEFAULT_PREFIX = "";

    public static final String DEFAULT_SUFFIX = ".ftl";

    /**
     * Well-known FreeMarker keys which will be passed to FreeMarker's Configuration.
     */
    private Map<String, String> settings = new HashMap<String, String>();

    /**
     * Comma-separated list of template paths.
     */
    private String[] templateLoaderPath = new String[] { DEFAULT_TEMPLATE_LOADER_PATH };

    /**
     * Prefer file system access for template loading. File system access enables hot
     * detection of template changes.
     */
    private boolean preferFileSystemAccess = true;

    public FreeMarkerProperties() {
        super(DEFAULT_PREFIX, DEFAULT_SUFFIX);
    }

    public Map<String, String> getSettings() {
        return this.settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }

    public String[] getTemplateLoaderPath() {
        return this.templateLoaderPath;
    }

    public boolean isPreferFileSystemAccess() {
        return this.preferFileSystemAccess;
    }

    public void setPreferFileSystemAccess(boolean preferFileSystemAccess) {
        this.preferFileSystemAccess = preferFileSystemAccess;
    }

    public void setTemplateLoaderPath(String... templateLoaderPaths) {
        this.templateLoaderPath = templateLoaderPaths;
    }

    /**
     * Apply the given properties to a {@link AbstractTemplateViewResolver}. Use Object in
     * signature to avoid runtime dependency on MVC, which means that the template engine
     * can be used in a non-web application.
     * @param viewResolver the resolver to apply the properties to.
     */
    public void applyToViewResolver(Object viewResolver) {
        Assert.isInstanceOf(FreeMarkerViewResolver.class, viewResolver,
                "ViewResolver is not an instance of AbstractTemplateViewResolver :"
                        + viewResolver);
        FreeMarkerViewResolver resolver = (FreeMarkerViewResolver) viewResolver;
        resolver.setPrefix(getPrefix());
        resolver.setSuffix(getSuffix());
        if (getContentType() != null) {
            resolver.setSupportedMediaTypes(Collections.singletonList(MediaType.asMediaType(getContentType())));
        }
        resolver.setViewNames(getViewNames());
        // The resolver usually acts as a fallback resolver (e.g. like a
        // InternalResourceViewResolver) so it needs to have low precedence
        resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 5);
    }

}

