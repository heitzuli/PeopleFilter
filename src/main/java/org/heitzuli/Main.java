package org.heitzuli;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String fileName = args[0];
        var objectMapper = new ObjectMapper();
        try (var scanner = new Scanner(System.in)) {
            var file = new File(fileName);
            if (!file.exists()) {
                System.out.println("File " + fileName + " doesn't exist.");
                System.exit(-1); //Exits with error
            }

            List<Person> people = objectMapper.readValue(file, new TypeReference<>() { });

            var filterField = getField(scanner, "Filter by field (firstName, lastName, age, gender). Empty input to skip:");
            if (filterField != PersonField.NONE) {
                people = filterPeople(scanner, people, filterField);
            }

            var sortingField = getField(scanner, "Sort by field (firstName, lastName, age, gender). Empty input to skip:");
            if(sortingField != PersonField.NONE) {
                people = sortPeople(scanner, sortingField, filterField, people);
            }

            printPeople(people);
        } catch (JsonParseException | InvalidFormatException jsonException) {
            System.out.println("JSON file " + fileName + " is not of correct format or schema");
        } catch (IOException e) {
            System.out.println("Could not open file :(");
        }
    }

    private static List<Person> sortPeople(Scanner scanner, PersonField sortingField, PersonField filterField, List<Person> people) {
        var sortingOrder = getSortingOrder(scanner);

        Comparator<Person> comparator = switch (sortingField) {
            case FIRSTNAME -> Comparator.comparing(Person::firstName);
            case LASTNAME -> Comparator.comparing(Person::lastName);
            case GENDER -> Comparator.comparing(Person::gender);
            case AGE -> Comparator.comparingInt(Person::age);
            default -> throw new IllegalStateException("Unexpected value: " + filterField);
        };

        people = switch (sortingOrder) {
            case ASCENDING -> people.stream().sorted(comparator).toList();
            case DESCENDING -> people.stream().sorted(comparator.reversed()).toList();
        };
        return people;
    }

    private static List<Person> filterPeople(Scanner scanner, List<Person> people, PersonField filterField) {
        var filterContent = getFilterContent(scanner);
        people = switch (filterField) {
            case FIRSTNAME ->
                    people.stream().filter(person -> person.firstName().equals(filterContent)).toList();
            case LASTNAME ->
                    people.stream().filter(person -> person.lastName().equals(filterContent)).toList();
            case GENDER -> people.stream().filter(person -> person.gender().equals(filterContent)).toList();
            default -> throw new IllegalStateException("Unexpected value: " + filterField);
        };
        return people;
    }

    // Add ascending/descending i getSortingOrder
    private static PersonField getField(Scanner scanner, String question) {
        PersonField answer = null;
        while (answer == null) {
            System.out.println(question);
            var input = scanner.nextLine();
            if (input.equals("firstName") || input.equals("lastName") || input.equals("age") || input.equals("gender")) {
                answer = PersonField.valueOf(input.toUpperCase());
            } else if (input.isEmpty()) {
                answer = PersonField.NONE;
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

    private static void printPeople(List<Person> people) {
        for (Person person : people) {
            System.out.println("First name: " + person.firstName());
            System.out.println("Last name: " + person.lastName());
            System.out.println("Age: " + person.age());
            System.out.println("Gender: " + person.gender());
            System.out.println();
        }
    }
}