package ru.ulstu.is.sbapp.student.service.exeption;

public class ConsignmentNotFoundException extends RuntimeException {
    public ConsignmentNotFoundException(Long id) {
        super(String.format("Consignment with id [%s] is not found", id));
    }
}
