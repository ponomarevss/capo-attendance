package ru.sspo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sspo.model.Attendance;
import ru.sspo.model.Training;
import ru.sspo.service.TrainingService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping("/{id}")
    public ResponseEntity<Training> getById(@PathVariable Long id) {
        Training training = trainingService.findById(id).orElseThrow(() ->
                new NoSuchElementException("Training not found"));
        return ResponseEntity.ok(training);
    }

    @GetMapping
    public ResponseEntity<List<Training>> getAll() {
        return ResponseEntity.ok(trainingService.findAll());
    }

    @PostMapping
    public ResponseEntity<Training> create(@RequestBody Training training) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainingService.create(training));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trainingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/attendances")
    public ResponseEntity<List<Attendance>> getAttendances(@PathVariable Long id) {
        return ResponseEntity.ok(trainingService.findAttendances(id));
    }
}
