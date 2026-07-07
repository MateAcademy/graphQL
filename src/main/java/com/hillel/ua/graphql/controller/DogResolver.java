package com.hillel.ua.graphql.controller;

import com.hillel.ua.graphql.entities.pet.Dog;
import com.hillel.ua.graphql.entities.person.Person;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DogResolver {

    @SchemaMapping(typeName = "Dog")
    public List<Person> owners(Dog dog, @Argument Boolean includePreviousOwners) {
        if (Boolean.TRUE.equals(includePreviousOwners)) {
            List<Person> all = new ArrayList<>(dog.owners());
            all.addAll(dog.previousOwners());
            return all;
        }
        return dog.owners();
    }
}
