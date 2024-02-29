package com.example.sakila.entities;

import com.example.sakila.converters.YearConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    @Convert(converter = YearConverter.class)
    private Integer releaseYear;

    @Column(name = "length")
    private Short length;

    @ManyToMany(mappedBy = "films")
    private List<Actor> cast;
}
