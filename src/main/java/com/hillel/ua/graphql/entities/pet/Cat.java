package com.hillel.ua.graphql.entities.pet;

import com.hillel.ua.graphql.entities.vaccine.Vaccine;

import java.time.LocalDate;
import java.util.List;

public record Cat(
        Long id,
        String name,
        String color,
        PetKind kind,
        List<Pet> friends,
        LocalDate dateOfBirth,
        Boolean doesMeow,
        List<Vaccine> vaccines
) implements Pet {
}
