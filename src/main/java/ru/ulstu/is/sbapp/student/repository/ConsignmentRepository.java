package ru.ulstu.is.sbapp.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ulstu.is.sbapp.student.model.Consignment;

import java.util.List;

public interface ConsignmentRepository extends JpaRepository<Consignment, Long>{
}






