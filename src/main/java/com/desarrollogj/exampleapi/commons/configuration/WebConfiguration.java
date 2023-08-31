package com.desarrollogj.exampleapi.commons.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .exposedHeaders("Cache-Control",
                        "Content-Language",
                        "Content-Type",
                        "Expires",
                        "Last-Modified",
                        "Pragma",
                        "X-Source-Name",
                        "X-Source-Version",
                        "X-Generator",
                        "X-Global-Transaction-Id");
    }
}
