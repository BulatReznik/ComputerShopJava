package ru.ulstu.is.sbapp.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TestDto {
    @NotNull(message = "Id can't be null")
    private Long id;
    @NotBlank(message = "Name can't be null or empty")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String getData() {
        return String.format("%s %s", id, name);
    }

    @JsonIgnore
    public String getAnotherData() {
        return "Test";
    }
}
