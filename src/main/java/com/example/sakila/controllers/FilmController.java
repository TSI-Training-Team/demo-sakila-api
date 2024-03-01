package com.example.sakila.controllers;

import com.example.sakila.input.FilmInput;
import com.example.sakila.input.ValidationGroup;
import com.example.sakila.output.FilmDetailsOutput;
import com.example.sakila.output.FilmReferenceOutput;
import com.example.sakila.output.PagedOutput;
import com.example.sakila.services.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @PostMapping
    public ResponseEntity<FilmDetailsOutput> createFilm(@Validated(ValidationGroup.Create.class) @RequestBody FilmInput data) {
        final var film = filmService.createFilm(data);
        final var filmDetails = new FilmDetailsOutput(film);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(filmDetails);
    }

    @GetMapping
    public PagedOutput<FilmReferenceOutput> listFilms(
            @RequestParam(required = false) Optional<String> title,
            @RequestParam(required = false) Optional<Integer> page,
            @RequestParam(required = false) Optional<Integer> size
    ) {

        final var pageable = Pageable
                .ofSize(size.orElse(50))
                .withPage(page.map(p -> p - 1).orElse(0));

        final var films = title
                .map(value -> filmService.listFilmsByTitle(value, pageable))
                .orElseGet(() -> filmService.listFilms(pageable))
                .map(FilmReferenceOutput::new);

        return new PagedOutput<>(films);
    }

    @GetMapping("/{id}")
    public FilmDetailsOutput findFilm(@PathVariable Short id) {
        final var film = filmService.findFilm(id);
        return new FilmDetailsOutput(film);
    }

    @PatchMapping("/{id}")
    public FilmDetailsOutput updateFilm(
            @PathVariable Short id,
            @Validated(ValidationGroup.Update.class) @RequestBody FilmInput filmData) {
        final var film = filmService.updateFilm(id, filmData);
        return new FilmDetailsOutput(film);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Short id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }
}
