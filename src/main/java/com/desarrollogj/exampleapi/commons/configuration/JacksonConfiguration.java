package com.desarrollogj.exampleapi.commons.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfiguration {
    private static final LocalDateTimeSerializer LOCALDATETIMESERIALIZER =
            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"));

    @Bean
    public JavaTimeModule javaTimeModule() {
        var module = new JavaTimeModule();
        module.addSerializer(LOCALDATETIMESERIALIZER);
        return module;
    }

    @Bean
    public ObjectMapper objectMapper(JavaTimeModule javaTimeModule) {
        var objectMapper = new ObjectMapper();
        return objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(javaTimeModule)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
