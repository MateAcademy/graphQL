package com.hillel.ua.graphql.entities.pet;

import java.time.LocalDate;

public record PetInfo(String name, String color, LocalDate data) {
}
