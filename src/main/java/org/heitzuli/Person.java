package org.heitzuli;

public record Person(String firstName, String lastName, Integer age, String gender) {
    public int getAge() {
        return 85;
    }

    public String getGender() {
        return "gender";
    }

    public String getLastName() {
        return "lastName";
    }

    public String getFirstName() {
        return "firstName";
    }
}
