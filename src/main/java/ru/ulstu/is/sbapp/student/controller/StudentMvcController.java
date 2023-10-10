package ru.ulstu.is.sbapp.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ulstu.is.sbapp.student.service.StudentService;

import javax.validation.Valid;

@Controller
@RequestMapping("/student")
public class StudentMvcController {
    private final StudentService studentService;

    public StudentMvcController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getStudents(Model model) {
        model.addAttribute("students",
                studentService.findAllStudents().stream()
                        .map(StudentDto::new)
                        .toList());
        return "student";
    }

    @GetMapping(value = {"/edit", "/edit/{id}"})
    public String editStudent(@PathVariable(required = false) Long id,
                              Model model) {
        if (id == null || id <= 0) {
            model.addAttribute("studentDto", new StudentDto());
        } else {
            model.addAttribute("studentId", id);
            model.addAttribute("studentDto", new StudentDto(studentService.findStudent(id)));
        }
        return "student-edit";
    }

    @PostMapping(value = {"", "/{id}"})
    public String saveStudent(@PathVariable(required = false) Long id,
                              @ModelAttribute @Valid StudentDto studentDto,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "student-edit";
        }
        if (id == null || id <= 0) {
            studentService.addStudent(studentDto.getFirstName(), studentDto.getLastName());
        } else {
            studentService.updateStudent(id, studentDto.getFirstName(), studentDto.getLastName());
        }
        return "redirect:/student";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student";
    }
}