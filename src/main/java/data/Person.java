package data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
    private String name;
    private int age;

    public Person() {
        // Needed by Jackson deserialization
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public int getAge() {
        return age;
    }
}