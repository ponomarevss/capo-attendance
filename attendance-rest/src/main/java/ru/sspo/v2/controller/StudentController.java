package ru.sspo.v2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sspo.v2.model.Attendance;
import ru.sspo.v2.model.Student;
import ru.sspo.v2.service.StudentService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<Student> getById(@PathVariable Long id) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found"));
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(student));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/attendances")
    public ResponseEntity<List<Attendance>> getAttendances(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findAttendances(id));
    }
}
