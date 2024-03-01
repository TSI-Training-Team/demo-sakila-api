package com.example.sakila.services;

import com.example.sakila.entities.Actor;
import com.example.sakila.input.ActorInput;
import com.example.sakila.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public Actor createActor(ActorInput data) {
        final var actor = new Actor();
        actor.setFirstName(data.getFirstName());
        actor.setLastName(data.getLastName());
        final var saved = actorRepository.save(actor);
        return actorRepository.findById(saved.getId()).orElseThrow();
    }

    public Page<Actor> listActors(Pageable pageable) {
        return actorRepository.findAll(pageable);
    }

    public Page<Actor> listActorsByName(String name, Pageable pageable) {
        return actorRepository.findByFullNameContainingIgnoreCase(name, pageable);
    }

    public Actor findActor(Short id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        NOT_FOUND,
                        String.format("No such actor with id '%d'.", id)));
    }

    public Actor updateActor(Short id, ActorInput data) {
        final var actor = findActor(id);
        if (data.getFirstName() != null) actor.setFirstName(data.getFirstName());
        if (data.getLastName() != null) actor.setLastName(data.getLastName());
        actorRepository.save(actor);
        return actorRepository.findById(id).orElseThrow();
    }

    public void deleteActor(Short id) {
        actorRepository.deleteById(id);
    }

}
