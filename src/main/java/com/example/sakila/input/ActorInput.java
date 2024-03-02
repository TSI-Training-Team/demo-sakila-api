package com.example.sakila.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import static com.example.sakila.input.ValidationGroup.Create;

@Data
@AllArgsConstructor
public class ActorInput {
    @NotNull(groups = {Create.class})
    @Size(min = 1, max = 45)
    private @NonNull String firstName;

    @NotNull(groups = {Create.class})
    @Size(min = 1, max = 45)
    private @NonNull String lastName;
}
