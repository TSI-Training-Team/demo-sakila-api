package com.example.sakila.output;

import com.example.sakila.entities.Film;
import lombok.Data;

import java.time.Year;

@Data
public class FilmReferenceOutput {
    private Short id;
    private String title;
    private Year releaseYear;

    public FilmReferenceOutput(Film film) {
        this.id = film.getId();
        this.title = film.getTitle();
        this.releaseYear = film.getReleaseYear();
    }
}
