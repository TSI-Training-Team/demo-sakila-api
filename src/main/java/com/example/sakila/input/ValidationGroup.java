package com.example.sakila.input;

import jakarta.validation.groups.Default;

public class ValidationGroup {

    public static final Class<? extends Default> create = Create.class;
    public static final Class<? extends Default> update = Create.class;

    public interface Create extends Default {}

    public interface Update extends Default {}
}