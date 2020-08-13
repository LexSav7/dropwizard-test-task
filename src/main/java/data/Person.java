package data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Person {
    @NotEmpty
    private String name;

    @NotNull
    @Min(1)
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