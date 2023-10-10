package ru.ulstu.is.sbapp.student.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ulstu.is.sbapp.student.model.Student;
import ru.ulstu.is.sbapp.student.repository.StudentRepository;
import ru.ulstu.is.sbapp.util.validation.ValidatorUtil;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ValidatorUtil validatorUtil;

    public StudentService(StudentRepository studentRepository,
                          ValidatorUtil validatorUtil) {
        this.studentRepository = studentRepository;
        this.validatorUtil = validatorUtil;
    }

    @Transactional
    public Student addStudent(String firstName, String lastName) {
        final Student student = new Student(firstName, lastName);
        validatorUtil.validate(student);
        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Student findStudent(Long id) {
        final Optional<Student> student = studentRepository.findById(id);
        return student.orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional
    public Student updateStudent(Long id, String firstName, String lastName) {
        final Student currentStudent = findStudent(id);
        currentStudent.setFirstName(firstName);
        currentStudent.setLastName(lastName);
        validatorUtil.validate(currentStudent);
        return studentRepository.save(currentStudent);
    }

    @Transactional
    public Student deleteStudent(Long id) {
        final Student currentStudent = findStudent(id);
        studentRepository.delete(currentStudent);
        return currentStudent;
    }

    @Transactional
    public void deleteAllStudents() {
        studentRepository.deleteAll();
    }
}
