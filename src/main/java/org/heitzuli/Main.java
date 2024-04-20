package org.heitzuli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var objectMapper = new ObjectMapper();
        try {
            var file = new File("src/main/resources/input.json");
            List<Person> people = objectMapper.readValue(file, new TypeReference<>() { });
            var sortingField = getUserInput();
            // Default if no input: sort descending on age
            var ascending = false;
            Comparator<Person> comparator = Comparator.comparingInt(Person::age);

            switch(sortingField) {
                case "firstName":
                    comparator = Comparator.comparing(Person::firstName);
                    break;
                case "lastName":
                    comparator = Comparator.comparing(Person::lastName);
                    break;
                case "gender":
                    comparator = Comparator.comparing(Person::gender);
                    break;
            }

            if(ascending) {
                people.sort(comparator);
            } else {
                people.sort(comparator.reversed());
            }

            for (Person person : people) {
                System.out.println(person);
            }

        } catch (IOException e) {
            System.out.println("Could not open file :(");
        }
    }

    private static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}