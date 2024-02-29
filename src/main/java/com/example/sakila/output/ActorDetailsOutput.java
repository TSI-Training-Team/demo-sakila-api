package com.example.sakila.output;

import com.example.sakila.entities.Actor;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ActorDetailsOutput {
    private Short id;
    private String firstName;
    private String lastName;
    private String fullName;
    private List<FilmReferenceOutput> films;

    public ActorDetailsOutput (Actor actor) {
        this.id = actor.getId();
        this.firstName = actor.getFirstName();
        this.lastName = actor.getLastName();
        this.fullName = actor.getFullName();
        this.films = actor.getFilms()
                .stream()
                .map(FilmReferenceOutput::new)
                .collect(Collectors.toList());
    }
}
