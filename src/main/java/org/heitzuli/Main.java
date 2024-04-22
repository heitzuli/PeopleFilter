package org.heitzuli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var objectMapper = new ObjectMapper();
        try {
            var file = new File("src/main/resources/input.json");
            List<Person> people = objectMapper.readValue(file, new TypeReference<>() {
            });
            String sortingField;
            SortingOrder sortingOrder;
            try (var scanner = new Scanner(System.in)) {
                sortingField = getSortingField(scanner);
                sortingOrder = getSortingOrder(scanner);
            }

            Comparator<Person> comparator = switch (sortingField) {
                case "firstName" -> Comparator.comparing(Person::firstName);
                case "lastName" -> Comparator.comparing(Person::lastName);
                case "gender" -> Comparator.comparing(Person::gender);
                default -> Comparator.comparingInt(Person::age);
            };

            switch (sortingOrder) {
                case ASCENDING -> people.sort(comparator);
                case DESCENDING -> people.sort(comparator.reversed());
            }

            for (Person person : people) {
                System.out.println(person);
            }

        } catch (IOException e) {
            System.out.println("Could not open file :(");
        }
    }
// Add ascending/descending i getSortingOrder
// Printa ut resultat i printPerson. Ev i for-loop så kallar man på printPerson som innehåller println.

    private static String getSortingField(Scanner scanner) {
        String answer = null;
        while (answer == null) {
            System.out.println("Filter by field (firstName, lastName, age, gender). Empty input to skip:");
            var input = scanner.nextLine();
            if (input.equals("firstName") || input.equals("lastName") || input.equals("age") || input.equals("gender") || input.isEmpty()) {
                answer = input;
            }
        }
        return answer;
    }

    // How to change to boolean?
    private static SortingOrder getSortingOrder(Scanner scanner) {
        String answer = null;
        while (answer == null) {
            System.out.println("Ascending? y/n");
            var input = scanner.nextLine();
            if (input.equals("y") || input.equals("n")) {
                answer = input;
            }
        } return answer.equals("y") ? SortingOrder.ASCENDING : SortingOrder.DESCENDING;
    }
}