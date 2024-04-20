package org.heitzuli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var objectMapper = new ObjectMapper();
        try {
            var file = new File("src/main/resources/input.json");
            List<Person> filteredPersons = objectMapper.readValue(file, new TypeReference<>() {
            });
            // Input from user: what to filter by
            try(var scanner = new Scanner(System.in)) {
                System.out.print("Enter the field to filter by (firstName, lastName, age, gender): ");
                var userFilterValue = scanner.nextLine();

                switch (userFilterValue) {
                    case "firstName" -> filteredPersons = filteredPersons.stream()
                            .filter(person -> person.getFirstName().equals(userFilterValue))
                            .toList();
                    case "lastName" -> filteredPersons = filteredPersons.stream()
                            .filter(person -> person.getLastName().equals(userFilterValue))
                            .toList();
                    case "age" -> {
                        int age = 0;
                        try {
                            age = Integer.parseInt(userFilterValue);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input: Please enter a valid integer.");
                        }
                        filteredPersons = filteredPersons.stream()
                                .filter(person -> person.getAge() > age)
                                .toList();
                    }
                    case "gender" -> filteredPersons = filteredPersons.stream()
                            .filter(person -> person.getGender().equals(userFilterValue))
                            .toList();
                    default -> System.out.println("Invalid filter field! YOu can filter by: firstName, lastName, age, gender");
                }
            }
            System.out.println(filteredPersons);
        } catch (IOException e) {
            System.out.println("Could not open file :(");
        }
    }
}