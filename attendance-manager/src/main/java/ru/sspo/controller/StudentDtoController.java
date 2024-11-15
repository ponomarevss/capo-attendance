package ru.sspo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sspo.client.StudentResponse;
import ru.sspo.dto.StudentDto;
import ru.sspo.service.GroupDtoService;
import ru.sspo.service.StudentDtoService;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentDtoController {

    private final StudentDtoService studentDtoService;
    private final GroupDtoService groupDtoService;

    @GetMapping
    public String getStudentsListPage(Model model) {
        List<StudentDto> students = studentDtoService.findAll()
                .stream()
                .sorted(
                        Comparator.comparing(StudentDto::getGroupId, Comparator.nullsLast(Long::compareTo))
                                .thenComparing(StudentDto::getLastname)
                )
                .toList();
        model.addAttribute("students", students);
        return "students-list-page.html";
    }

    @GetMapping("/{id}")
    public String getStudentPage(@PathVariable Long id, Model model) {
        Optional<StudentDto> optionalStudent = studentDtoService.findById(id);
        if (optionalStudent.isEmpty()) {
            return "not-found.html";
        }
        model.addAttribute("student", optionalStudent.get());
        return "student-page.html";
    }

    @GetMapping("/edit/{id}")
    public String showEditStudentForm(@PathVariable Long id, Model model) {
        Optional<StudentDto> optionalStudent = studentDtoService.findById(id);
        if (optionalStudent.isEmpty()) {
            return "not-found.html";
        }
        model.addAttribute("student", optionalStudent.get());
        model.addAttribute("groups", groupDtoService.findAll());
        return "edit-student.html";
    }

    @GetMapping("/create")
    public String showStudentCreateForm(Model model) {
        model.addAttribute("student", new StudentDto());
        model.addAttribute("groups", groupDtoService.findAll());
        return "create-student.html";
    }

    @PostMapping("/update")
    public String updateStudent(@ModelAttribute StudentResponse student) {
        studentDtoService.save(student);
        return "redirect:/students";
    }

    @PostMapping("/save")
    public String createStudent(@ModelAttribute StudentResponse student) {
        studentDtoService.save(student);
        return "redirect:/students";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentDtoService.deleteById(id);
        return "redirect:/students";
    }
}
