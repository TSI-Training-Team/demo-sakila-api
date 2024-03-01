package com.example.sakila.repositories;

import com.example.sakila.entities.Language;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<Language, Byte> {
}
