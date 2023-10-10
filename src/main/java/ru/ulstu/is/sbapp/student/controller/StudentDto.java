package ru.ulstu.is.sbapp.student.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.ulstu.is.sbapp.student.model.Student;

import javax.validation.constraints.NotBlank;

public class StudentDto {
    private long id;
    @NotBlank(message = "Firstname can't be null or empty")
    private String firstName;
    @NotBlank(message = "Lastname can't be null or empty")
    private String lastName;

    public StudentDto() {
    }

    public StudentDto(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
