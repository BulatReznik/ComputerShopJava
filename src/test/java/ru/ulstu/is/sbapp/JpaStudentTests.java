package ru.ulstu.is.sbapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ulstu.is.sbapp.student.model.Student;
import ru.ulstu.is.sbapp.student.service.StudentNotFoundException;
import ru.ulstu.is.sbapp.student.service.StudentService;

import java.util.List;

@SpringBootTest
public class JpaStudentTests {
    private static final Logger log = LoggerFactory.getLogger(JpaStudentTests.class);

    @Autowired
    private StudentService studentService;

    @Test
    void testStudentCreate() {
        studentService.deleteAllStudents();
        final Student student = studentService.addStudent("Иван", "Иванов");
        log.info(student.toString());
        Assertions.assertNotNull(student.getId());
    }

    @Test
    void testStudentRead() {
        studentService.deleteAllStudents();
        final Student student = studentService.addStudent("Иван", "Иванов");
        log.info(student.toString());
        final Student findStudent = studentService.findStudent(student.getId());
        log.info(findStudent.toString());
        Assertions.assertEquals(student, findStudent);
    }

    @Test
    void testStudentReadNotFound() {
        studentService.deleteAllStudents();
        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.findStudent(-1L));
    }

    @Test
    void testStudentReadAll() {
        studentService.deleteAllStudents();
        studentService.addStudent("Иван", "Иванов");
        studentService.addStudent("Петр", "Петров");
        final List<Student> students = studentService.findAllStudents();
        log.info(students.toString());
        Assertions.assertEquals(students.size(), 2);
    }

    @Test
    void testStudentReadAllEmpty() {
        studentService.deleteAllStudents();
        final List<Student> students = studentService.findAllStudents();
        log.info(students.toString());
        Assertions.assertEquals(students.size(), 0);
    }
}
