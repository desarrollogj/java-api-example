package com.desarrollogj.exampleapi.commons.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"local", "develop", "staging", "production"})
@Configuration
public class OpenApiConfiguration {
    @Autowired
    private BuildProperties buildProperties;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                .title(String.format("%s.%s", buildProperties.getGroup(), buildProperties.getName()))
                .version(buildProperties.getVersion())
                .description(buildProperties.get("description")));
    }
}
