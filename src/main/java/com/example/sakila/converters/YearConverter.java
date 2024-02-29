package com.example.sakila.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDate;

@Converter
public class YearConverter implements AttributeConverter<Integer, LocalDate> {
    @Override
    public LocalDate convertToDatabaseColumn(Integer year) {
        return LocalDate.ofYearDay(year, 1);
    }

    @Override
    public Integer convertToEntityAttribute(LocalDate date) {
        return date.getYear();
    }
}
