package com.example.sakila.services;

import com.example.sakila.entities.Film;
import com.example.sakila.input.FilmInput;
import com.example.sakila.repositories.ActorRepository;
import com.example.sakila.repositories.FilmRepository;
import com.example.sakila.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private LanguageRepository languageRepository;

    private void mapFilmFromInput(Film film, FilmInput data) {
        if (data.getTitle() != null) film.setTitle(data.getTitle());
        if (data.getDescription() != null) film.setDescription(data.getDescription());
        if (data.getReleaseYear() != null) film.setReleaseYear(Year.of(data.getReleaseYear()));
        if (data.getLength() != null) film.setLength(data.getLength());
        if (data.getRentalRate() != null) film.setRentalRate(data.getRentalRate());
        if (data.getRentalDuration() != null) film.setRentalDuration(data.getRentalDuration());

        if (data.getLanguageId() != null) {
            final var languageId = data.getLanguageId();
            final var language = languageRepository.findById(languageId)
                    .orElseThrow(() -> new ResponseStatusException(
                            NOT_FOUND,
                            String.format("No such language with id '%d'.", languageId)));
            film.setLanguage(language);
        }

        if (data.getCastIds() != null) {
            final var cast = data.getCastIds()
                    .stream()
                    .map(actorId -> actorRepository.findById(actorId)
                            .orElseThrow(() -> new ResponseStatusException(
                                    NOT_FOUND,
                                    String.format("No such actor with id '%d'.", actorId))))
                    .collect(Collectors.toList());
            film.setCast(cast);
        }
    }

    public Film createFilm(FilmInput data) {
        final var film = new Film();
        mapFilmFromInput(film, data);
        final var saved = filmRepository.save(film);
        return filmRepository.findById(saved.getId()).orElseThrow();
    }

    public Page<Film> listFilms(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    public Page<Film> listFilmsByTitle(String title, Pageable pageable) {
        return filmRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Film findFilm(Short id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        String.format("No such film with id '%d'.", id)));
    }

    public Film updateFilm(Short id, FilmInput data) {
        final var film = findFilm(id);
        mapFilmFromInput(film, data);
        filmRepository.save(film);
        return filmRepository.findById(id).orElseThrow();
    }

    public void deleteFilm(Short id) {
        filmRepository.deleteById(id);
    }

}
