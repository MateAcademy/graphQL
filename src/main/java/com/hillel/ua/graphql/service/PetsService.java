package com.hillel.ua.graphql.service;

import com.hillel.ua.graphql.dto.CreatePetInput;
import com.hillel.ua.graphql.dto.UpdatePetInput;
import com.hillel.ua.graphql.entities.pet.Bird;
import com.hillel.ua.graphql.entities.pet.Cat;
import com.hillel.ua.graphql.entities.pet.Dog;
import com.hillel.ua.graphql.entities.pet.Pet;
import com.hillel.ua.graphql.entities.pet.PetFilter;
import com.hillel.ua.graphql.entities.pet.PetKind;
import com.hillel.ua.graphql.entities.person.Person;
import com.hillel.ua.graphql.repository.PersonRepository;
import com.hillel.ua.graphql.repository.PetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.hillel.ua.graphql.entities.pet.PetKind.DOG;

@Service
@RequiredArgsConstructor
public class PetsService {

    private final PetsRepository petsRepository;
    private final PersonRepository personRepository;

    public List<Pet> findAll() {
        return petsRepository.findAll().stream()
                .sorted(Comparator.comparing(Pet::id))
                .toList();
    }

    public List<Pet> findByKind(PetKind kind) {
        return petsRepository.findAll().stream()
                .filter(p -> p.kind() == kind)
                .sorted(Comparator.comparing(Pet::id))
                .toList();
    }

    public List<Pet> findWithFilter(PetKind kind, PetFilter filter) {
        return petsRepository.findAll().stream()
                .filter(p -> kind == null || p.kind() == kind)
                .filter(p -> matchesAgeFilter(p, filter))
                .sorted(Comparator.comparing(Pet::id))
                .toList();
    }

    private boolean matchesAgeFilter(Pet pet, PetFilter filter) {
        if (filter == null) return true;
        if (pet.dateOfBirth() == null) return true;

        int age = Period.between(pet.dateOfBirth(), LocalDate.now()).getYears();

        if (filter.minAge() != null && age < filter.minAge()) return false;
        if (filter.maxAge() != null && age > filter.maxAge()) return false;
        return true;
    }

    public Pet findById(Long id) {
        return petsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found: " + id));
    }

    public Pet findByName(String name) {
        return petsRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Pet not found: " + name));
    }

    public Pet create(CreatePetInput input) {
        List<Pet> friends = input.getFriendIds() == null ? List.of() :
                input.getFriendIds().stream().map(this::findById).toList();

        return switch (input.getKind()) {
            case DOG -> {
                List<Person> owners = input.getOwnerIds() == null ? List.of() :
                        input.getOwnerIds().stream()
                                .map(id -> personRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Person not found: " + id)))
                                .toList();
                yield petsRepository.save(new Dog(null, input.getName(), input.getColor(), input.getKind(), friends, owners, List.of(), input.getDateOfBirth(), null, OffsetDateTime.now(), OffsetDateTime.now(), List.of()));
            }
            case CAT  -> petsRepository.save(new Cat( null, input.getName(), input.getColor(), input.getKind(), friends, input.getDateOfBirth(), null, List.of()));
            case BIRD -> petsRepository.save(new Bird(null, input.getName(), input.getColor(), input.getKind(), friends, input.getDateOfBirth(), List.of()));
            default   -> throw new RuntimeException("Unsupported pet kind: " + input.getKind());
        };
    }

    public Pet update(Long id, UpdatePetInput input) {
        Pet existing = findById(id);
        List<Pet> friends = input.getFriendIds() == null ? existing.friends() :
                input.getFriendIds().stream().map(this::findById).toList();

        return switch (existing) {
            case Dog dog -> {
                List<Person> newOwners;
                List<Person> previousOwners;
                if (input.getOwnerIds() == null) {
                    newOwners = dog.owners();
                    previousOwners = dog.previousOwners();
                } else {
                    newOwners = input.getOwnerIds().stream()
                            .map(ownerId -> personRepository.findById(ownerId)
                                    .orElseThrow(() -> new RuntimeException("Person not found: " + ownerId)))
                            .toList();
                    List<Person> merged = new ArrayList<>(dog.previousOwners());
                    merged.addAll(dog.owners());
                    previousOwners = List.copyOf(merged);
                }
                yield petsRepository.save(new Dog(
                        id,
                        input.getName()        != null ? input.getName()        : dog.name(),
                        input.getColor()       != null ? input.getColor()       : dog.color(),
                        input.getKind()        != null ? input.getKind()        : dog.kind(),
                        friends,
                        newOwners,
                        previousOwners,
                        input.getDateOfBirth() != null ? input.getDateOfBirth() : dog.dateOfBirth(),
                        dog.doesBark(),
                        dog.createdAt(),
                        OffsetDateTime.now(),
                        dog.vaccines()
                ));
            }
            case Cat cat -> petsRepository.save(new Cat(
                    id,
                    input.getName()        != null ? input.getName()        : cat.name(),
                    input.getColor()       != null ? input.getColor()       : cat.color(),
                    input.getKind()        != null ? input.getKind()        : cat.kind(),
                    friends,
                    input.getDateOfBirth() != null ? input.getDateOfBirth() : cat.dateOfBirth(),
                    cat.doesMeow(),
                    cat.vaccines()
            ));
            case Bird bird -> petsRepository.save(new Bird(
                    id,
                    input.getName()        != null ? input.getName()        : bird.name(),
                    input.getColor()       != null ? input.getColor()       : bird.color(),
                    input.getKind()        != null ? input.getKind()        : bird.kind(),
                    friends,
                    input.getDateOfBirth() != null ? input.getDateOfBirth() : bird.dateOfBirth(),
                    bird.vaccines()
            ));
            default -> throw new RuntimeException("Unknown pet type: " + existing.getClass());
        };
    }

    public Pet delete(Long id) {
        return petsRepository.delete(id)
                .orElseThrow(() -> new RuntimeException("Pet not found: " + id));
    }
}
