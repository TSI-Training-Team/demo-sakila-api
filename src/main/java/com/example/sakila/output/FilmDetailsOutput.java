package com.example.sakila.output;

import com.example.sakila.entities.Film;
import com.example.sakila.entities.Language;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FilmDetailsOutput {
    private Short id;
    private String title;
    private String description;
    private Year releaseYear;
    private Language language;
    private Short length;
    private Byte rentalDuration;
    private BigDecimal rentalRate;
    private List<ActorReferenceOutput> cast;

    public FilmDetailsOutput(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.description = film.getDescription();
        this.releaseYear = film.getReleaseYear();
        this.language = film.getLanguage();
        this.length = film.getLength();
        this.rentalDuration = film.getRentalDuration();
        this.rentalRate = film.getRentalRate();
        this.cast = film.getCast()
                .stream()
                .map(ActorReferenceOutput::new)
                .collect(Collectors.toList());
    }
}
