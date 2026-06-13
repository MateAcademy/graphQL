package com.hillel.ua.graphql.controller;

import com.hillel.ua.graphql.entities.Person;
import com.hillel.ua.graphql.entities.Pet;
import com.hillel.ua.graphql.entities.PetKind;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PetsController {

    private static final Person sasha = new Person(1L, "Sasha");
    private static final Person alla = new Person(2L, "Alla");
    private static final Person tania = new Person(3L, "Tania");

    private static final Pet SKIPPER = new Pet(1L, "Skipper", "black", PetKind.DOG, List.of(), List.of(sasha));
    private static final Pet SASHA = new Pet(3L, "Sasha", "red", PetKind.CAT, List.of(), List.of(alla));
    private static final Pet LUNA = new Pet(0L, "Luna", "cappuccino", PetKind.DOG, List.of(SKIPPER, SASHA), List.of(sasha, alla, tania));

    private final List<Pet> pets = List.of(SKIPPER, SASHA, LUNA);

    @QueryMapping
    public List<Pet> pets(@Argument PetKind kind) {
        if (kind == null) {
            return pets;
        }
        return pets.stream()
                .filter(x -> x.kind() == kind)
                .toList();
    }

    @QueryMapping
    public Pet pet(@Argument String id, @Argument String name) {
        if (id == null && name == null) {
            return null;
        }

        if (id == null) {
            return pets.stream().filter(pet -> name.equals(pet.name())).findFirst().orElse(null);
        }

        long petId = Long.parseLong(id);
        return pets.stream()
                .filter(p -> p.id().equals(petId))
                .findFirst()
                .orElse(null);
    }
}
