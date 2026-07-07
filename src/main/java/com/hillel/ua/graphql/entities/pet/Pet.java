package com.hillel.ua.graphql.entities.pet;

import com.hillel.ua.graphql.entities.vaccine.Vaccine;

import java.time.LocalDate;
import java.util.List;

public interface Pet {
    Long id();
    String name();
    String color();
    PetKind kind();
    List<Pet> friends();
    LocalDate dateOfBirth();
    List<Vaccine> vaccines();
}
