package com.hillel.ua.graphql.entities.vaccine;

import java.time.LocalDate;

public record Vaccine(String name, LocalDate vaccinatedAt, LocalDate nextDueDate) {
}
