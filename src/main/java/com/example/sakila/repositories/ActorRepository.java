package com.example.sakila.repositories;

import com.example.sakila.entities.Actor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActorRepository extends PagingAndSortingRepository<Actor, Short>, CrudRepository<Actor, Short> {
    Page<Actor> findByFullNameContainingIgnoreCase(String name, Pageable page);
}
