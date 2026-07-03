package com.hillel.ua.graphql.controller;

import com.hillel.ua.graphql.dto.CreatePetInput;
import com.hillel.ua.graphql.dto.UpdatePetInput;
import com.hillel.ua.graphql.entities.pet.Pet;
import com.hillel.ua.graphql.entities.pet.PetFilter;
import com.hillel.ua.graphql.entities.pet.PetInfo;
import com.hillel.ua.graphql.entities.pet.PetKind;
import com.hillel.ua.graphql.service.PetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PetsController {

    private final PetsService petsService;

    @QueryMapping
    List<PetInfo> pets() {
        return List.of(
                new PetInfo("Luna", "cappuccino", LocalDate.now()),
                new PetInfo("Skipper", "black", LocalDate.now()));
    }

    @QueryMapping
    public List<Pet> getPets(@Argument PetKind kind, @Argument PetFilter filter) {
        return petsService.findWithFilter(kind, filter);
    }

    @QueryMapping
    public Pet getPet(@Argument String id, @Argument String name) {
        if (id == null && name == null) {
            return null;
        }
        if (id == null) {
            return petsService.findByName(name);
        }
        return petsService.findById(Long.parseLong(id));
    }

    @MutationMapping
    public Pet createPet(@Argument CreatePetInput input) {
        return petsService.create(input);
    }

    @MutationMapping
    public Pet updatePet(@Argument Long id, @Argument UpdatePetInput input) {
        return petsService.update(id, input);
    }

    @MutationMapping
    public Pet deletePet(@Argument Long id) {
        return petsService.delete(id);
    }
}
