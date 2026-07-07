package com.hillel.ua.graphql.repository;

import com.hillel.ua.graphql.entities.person.Person;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepository {

    private final List<Person> storage = List.of(
            new Person(1L, "Sasha"),
            new Person(2L, "Alla"),
            new Person(3L, "Tania")
    );

    public List<Person> findAll() {
        return storage;
    }

    public Optional<Person> findById(Long id) {
        return storage.stream().filter(p -> p.id() == id).findFirst();
    }
}
