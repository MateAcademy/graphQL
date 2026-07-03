package com.hillel.ua.graphql.controller;

import com.hillel.ua.graphql.entities.pet.Bird;
import com.hillel.ua.graphql.entities.pet.Cat;
import com.hillel.ua.graphql.entities.pet.Dog;
import com.hillel.ua.graphql.entities.vaccine.Vaccine;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PetResolver {

    @SchemaMapping(typeName = "Dog")
    public List<Vaccine> vaccines(Dog dog, @Argument Boolean onlyOverdue) {
        return filterVaccines(dog.vaccines(), onlyOverdue);
    }

    @SchemaMapping(typeName = "Cat")
    public List<Vaccine> vaccines(Cat cat, @Argument Boolean onlyOverdue) {
        return filterVaccines(cat.vaccines(), onlyOverdue);
    }

    @SchemaMapping(typeName = "Bird")
    public List<Vaccine> vaccines(Bird bird, @Argument Boolean onlyOverdue) {
        return filterVaccines(bird.vaccines(), onlyOverdue);
    }

    private List<Vaccine> filterVaccines(List<Vaccine> vaccines, Boolean onlyOverdue) {
        if (Boolean.TRUE.equals(onlyOverdue)) {
            return vaccines.stream()
                    .filter(vaccine -> vaccine.nextDueDate().isBefore(LocalDate.now()))
                    .toList();
        }
        return vaccines;
    }
}
