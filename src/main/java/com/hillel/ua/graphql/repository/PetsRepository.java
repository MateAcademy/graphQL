package com.hillel.ua.graphql.repository;

import com.hillel.ua.graphql.entities.pet.Bird;
import com.hillel.ua.graphql.entities.pet.Cat;
import com.hillel.ua.graphql.entities.pet.Dog;
import com.hillel.ua.graphql.entities.pet.Pet;
import com.hillel.ua.graphql.entities.pet.PetKind;
import com.hillel.ua.graphql.entities.person.Person;
import com.hillel.ua.graphql.entities.vaccine.Vaccine;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@RequiredArgsConstructor
public class PetsRepository {

    private final PersonRepository personRepository;

    private final List<Pet> storage = new ArrayList<>();

    private final List<Vaccine> vaccineList1 = List.of(
            new Vaccine("Rabies",     LocalDate.of(2023, 1, 10), LocalDate.of(2024, 1, 10)),  // просрочена
            new Vaccine("Parvovirus", LocalDate.of(2024, 6, 1),  LocalDate.now().plusDays(30)) // актуальна
    );

    private final List<Vaccine> vaccineList2 = List.of(
            new Vaccine("Distemper", LocalDate.of(2022, 5, 15), LocalDate.of(2023, 5, 15))    // просрочена
    );

    private final AtomicLong idSequence = new AtomicLong(1);

    @PostConstruct
    void init() {
        Person sasha_person = personRepository.findById(1L).orElseThrow();
        Person alla_person  = personRepository.findById(2L).orElseThrow();
        Person tania_person = personRepository.findById(3L).orElseThrow();

        Pet dog_skipper = this.save(new Dog(null, "dog_Skipper", "black",      PetKind.DOG, List.of(),                      List.of(sasha_person),             List.of(tania_person, alla_person), LocalDate.of(2020, 3, 10), true,  OffsetDateTime.now(), OffsetDateTime.now(), vaccineList1));
        Pet cat_sasha   = this.save(new Cat(null, "cat_Sasha",   "red",        PetKind.CAT, List.of(),                      LocalDate.of(2018, 7, 22), true,  vaccineList1));
        Pet dog_luna    = this.save(new Dog(null, "dog_Luna",    "cappuccino", PetKind.DOG, List.of(dog_skipper, cat_sasha), List.of(sasha_person, alla_person), List.of(tania_person),             LocalDate.of(2022, 1, 5),  false, OffsetDateTime.now(), OffsetDateTime.now(), vaccineList2));
    }

    public List<Pet> findAll() {
        return List.copyOf(storage);
    }

    public Optional<Pet> findById(Long id) {
        return storage.stream().filter(p -> p.id().equals(id)).findFirst();
    }

    public Optional<Pet> findByName(String name) {
        return storage.stream().filter(p -> name.equals(p.name())).findFirst();
    }

    public Optional<Pet> delete(Long id) {
        Optional<Pet> found = findById(id);
        found.ifPresent(storage::remove);
        return found;
    }

    public Pet save(Pet pet) {
        Long id = pet.id() != null ? pet.id() : this.idSequence.getAndIncrement();
        Pet toSave = switch (pet) {
            case Dog  dog  -> new Dog( id, dog.name(),  dog.color(),  dog.kind(),  dog.friends(),  dog.owners(), dog.previousOwners(), dog.dateOfBirth(), dog.doesBark(), dog.createdAt(), dog.updatedAt(), dog.vaccines());
            case Cat  cat  -> new Cat( id, cat.name(),  cat.color(),  cat.kind(),  cat.friends(),  cat.dateOfBirth(), cat.doesMeow(), cat.vaccines());
            case Bird bird -> new Bird(id, bird.name(), bird.color(), bird.kind(), bird.friends(), bird.dateOfBirth(), bird.vaccines());
            default        -> throw new RuntimeException("Unknown pet type: " + pet.getClass());
        };
        storage.removeIf(p -> p.id().equals(id));
        storage.add(toSave);
        return toSave;
    }
}
