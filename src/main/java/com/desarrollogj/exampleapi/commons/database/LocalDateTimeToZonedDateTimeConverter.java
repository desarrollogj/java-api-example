package com.desarrollogj.exampleapi.commons.database;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ReadingConverter
public class LocalDateTimeToZonedDateTimeConverter implements Converter<LocalDateTime, ZonedDateTime> {
    @Override
    public ZonedDateTime convert(LocalDateTime source) {
        return  ZonedDateTime.ofLocal(source, ZoneId.systemDefault(), null);
    }
}
