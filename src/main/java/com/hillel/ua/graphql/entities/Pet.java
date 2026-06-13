package com.hillel.ua.graphql.entities;

import java.util.List;

public record Pet(Long id, String name, String color, PetKind kind, List<Pet> friends, List<Person> persons) {
}