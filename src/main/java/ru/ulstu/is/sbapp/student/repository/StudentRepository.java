package ru.ulstu.is.sbapp.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.student.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
