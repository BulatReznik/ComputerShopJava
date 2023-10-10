package ru.ulstu.is.sbapp.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.student.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
