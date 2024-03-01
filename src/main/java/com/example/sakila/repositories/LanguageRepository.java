package com.example.sakila.repositories;

import com.example.sakila.entities.Film;
import com.example.sakila.entities.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LanguageRepository extends CrudRepository<Language, Byte> {}
