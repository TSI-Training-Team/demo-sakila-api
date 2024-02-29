package com.example.sakila.controllers;

import com.example.sakila.input.ActorInput;
import com.example.sakila.output.ActorDetailsOutput;
import com.example.sakila.output.ActorReferenceOutput;
import com.example.sakila.output.PagedOutput;
import com.example.sakila.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.sakila.input.ValidationGroup.Create;
import static com.example.sakila.input.ValidationGroup.Update;

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @PostMapping
    public ResponseEntity<ActorDetailsOutput> createActor(@Validated(Create.class) @RequestBody ActorInput data) {
        final var actor = actorService.createActor(data);
        final var actorDetails = new ActorDetailsOutput(actor);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(actorDetails);
    }

    @GetMapping
    public PagedOutput<ActorReferenceOutput> listActors(
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<Integer> page,
            @RequestParam(required = false) Optional<Integer> size
    ) {

        final var pageable = Pageable
                .ofSize(size.orElse(50))
                .withPage(page.map(p -> p - 1).orElse(0));

        final var actors = name
                .map(value -> actorService.listActorsByName(value, pageable))
                .orElseGet(() -> actorService.listActors(pageable))
                .map(ActorReferenceOutput::new);

        return new PagedOutput<>(actors);
    }

    @GetMapping("/{id}")
    public ActorDetailsOutput findActor(@PathVariable Short id) {
        final var actor = actorService.findActor(id);
        return new ActorDetailsOutput(actor);
    }

    @PatchMapping("/{id}")
    public ActorDetailsOutput updateActor(
            @PathVariable Short id,
            @Validated(Update.class) @RequestBody ActorInput actorData) {
        final var actor = actorService.updateActor(id, actorData);
        return new ActorDetailsOutput(actor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable Short id) {
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }
}
