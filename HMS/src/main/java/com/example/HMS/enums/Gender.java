package com.example.HMS.enums;

import lombok.Getter;

public enum Gender {

    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

}
