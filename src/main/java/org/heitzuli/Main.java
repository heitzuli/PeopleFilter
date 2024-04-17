package org.heitzuli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var objectMapper = new ObjectMapper();
        try {
            var file = new File("src/main/resources/input.json");
            System.out.println(file.exists());
            List<Person> persons = objectMapper.readValue(file, new TypeReference<List<Person>>() {});
            persons.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Could not open file :(");
            e.printStackTrace();
        }
    }
}