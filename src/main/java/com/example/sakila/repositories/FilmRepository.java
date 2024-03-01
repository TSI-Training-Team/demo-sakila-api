package com.example.sakila.repositories;

import com.example.sakila.entities.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FilmRepository extends PagingAndSortingRepository<Film, Short>, CrudRepository<Film, Short> {
    Page<Film> findByTitleContainingIgnoreCase(String title, Pageable page);
}
