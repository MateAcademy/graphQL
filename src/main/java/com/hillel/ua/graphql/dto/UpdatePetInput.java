package com.hillel.ua.graphql.dto;

import com.hillel.ua.graphql.entities.pet.PetKind;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class UpdatePetInput {
    private String name;
    private String color;
    private PetKind kind;
    private List<Long> friendIds;
    private List<Long> ownerIds;
    private LocalDate dateOfBirth;
    private String profilePictureUrl;
}
