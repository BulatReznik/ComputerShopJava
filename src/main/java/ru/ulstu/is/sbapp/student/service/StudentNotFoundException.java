package ru.ulstu.is.sbapp.student.service;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super(String.format("Student with id [%s] is not found", id));
    }
}
