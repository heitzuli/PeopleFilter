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
        try (var scanner = new Scanner(System.in)) {
            var file = new File("src/main/resources/input.json");
            List<Person> people = objectMapper.readValue(file, new TypeReference<>() {
            });

            // Filterfield empty: should not ask for content
            // Sorting field empty: should not ask for order
            var filterField = getFilterField(scanner);
            var filterContent = getFilterContent(scanner);
            var sortingField = getSortingField(scanner);
            var sortingOrder = getSortingOrder(scanner);

            Comparator<Person> comparator = switch (sortingField) {
                case "firstName" -> Comparator.comparing(Person::firstName);
                case "lastName" -> Comparator.comparing(Person::lastName);
                case "gender" -> Comparator.comparing(Person::gender);
                default -> Comparator.comparingInt(Person::age);
            };

            if (!filterField.isEmpty()) {
                people = switch (filterField) {
                    case "firstName" ->
                            people.stream().filter(person -> person.firstName().equals(filterContent)).toList();
                    case "lastName" ->
                            people.stream().filter(person -> person.lastName().equals(filterContent)).toList();
                    case "gender" -> people.stream().filter(person -> person.gender().equals(filterContent)).toList();
                    default -> throw new IllegalStateException("Unexpected value: " + filterField);
                };
            }

            people = switch (sortingOrder) {
                case ASCENDING -> people.stream().sorted(comparator).toList();
                case DESCENDING -> people.stream().sorted(comparator.reversed()).toList();
            };
            printPerson(people);

        } catch (IOException e) {
            System.out.println("Could not open file :(");
        }
    }
// Add ascending/descending i getSortingOrder

    private static String getSortingField(Scanner scanner) {
        String answer = null;
        while (answer == null) {
            System.out.println("Sort by field (firstName, lastName, age, gender). Empty input to skip:");
            var input = scanner.nextLine();
            if (input.equals("firstName") || input.equals("lastName") || input.equals("age") || input.equals("gender") || input.isEmpty()) {
                answer = input;
            }
        }
        return answer;
    }

    private static SortingOrder getSortingOrder(Scanner scanner) {
        String answer = null;
        while (answer == null) {
            System.out.println("Ascending? y/n");
            var input = scanner.nextLine();
            if (input.equals("y") || input.equals("n")) {
                answer = input;
            }
        }
        return answer.equals("y") ? SortingOrder.ASCENDING : SortingOrder.DESCENDING;
    }

    private static String getFilterField(Scanner scanner) {
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

    private static String getFilterContent(Scanner scanner) {
        String answer = null;
        while (answer == null) {
            System.out.println("Value:");
            var input = scanner.nextLine();
            if (!input.isEmpty()) {
                answer = input;
            }
        }
        return answer;
    }

    private static void printPerson(List<Person> people) {
        for (Person person : people) {
            System.out.println("First name: " + person.firstName());
            System.out.println("Last name: " + person.lastName());
            System.out.println("Age: " + person.age());
            System.out.println("Gender: " + person.gender());
            System.out.println();
        }
    }
}