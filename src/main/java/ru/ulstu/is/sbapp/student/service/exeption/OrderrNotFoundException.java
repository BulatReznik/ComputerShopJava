package ru.ulstu.is.sbapp.student.service.exeption;

public class OrderrNotFoundException extends RuntimeException {
    public OrderrNotFoundException(Long id) {
        super(String.format("Order with id [%s] is not found", id));
    }
}
