package com.hillel.ua.graphql.entities.pet;

import com.hillel.ua.graphql.entities.person.Person;
import com.hillel.ua.graphql.entities.vaccine.Vaccine;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public record Dog(
        Long id,
        String name,
        String color,
        PetKind kind,
        List<Pet> friends,
        List<Person> owners,
        List<Person> previousOwners,
        LocalDate dateOfBirth,
        Boolean doesBark,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<Vaccine> vaccines
) implements Pet {
}
