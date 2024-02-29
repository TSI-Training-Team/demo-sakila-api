package com.example.sakila.output;

import com.example.sakila.entities.Film;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FilmReferenceOutput {
    private Short id;
    private String title;
    private Integer releaseYear;

    public FilmReferenceOutput(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.releaseYear = film.getReleaseYear();
    }
}
