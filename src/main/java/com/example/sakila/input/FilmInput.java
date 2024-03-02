package com.example.sakila.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class FilmInput {
    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 128)
    private String title;

    @NotNull(groups = {ValidationGroup.Create.class})
    @Size(min = 1, max = 500)
    private String description;

    @NotNull(groups = {ValidationGroup.Create.class})
    @Min(0)
    private Integer releaseYear;

    @NotNull(groups = {ValidationGroup.Create.class})
    private Byte languageId;

    @NotNull(groups = {ValidationGroup.Create.class})
    @Min(1)
    private Short length;

    @NotNull(groups = {ValidationGroup.Create.class})
    private Byte rentalDuration;

    @NotNull(groups = {ValidationGroup.Create.class})
    private BigDecimal rentalRate;

    private List<Short> castIds;
}
