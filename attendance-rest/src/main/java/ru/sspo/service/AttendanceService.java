package ru.sspo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sspo.aspect.logging.Logging;
import ru.sspo.model.Attendance;
import ru.sspo.repository.AttendanceRepository;
import ru.sspo.repository.StudentRepository;
import ru.sspo.repository.TrainingRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Logging
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final TrainingRepository trainingRepository;

    public Optional<Attendance> findById(Long id) {
        return attendanceRepository.findById(id);
    }

    public List<Attendance> findAll() {
        return attendanceRepository.findAll();
    }

    public Attendance create(Attendance attendance) {
        validateAttendance(attendance);
        return attendanceRepository.save(attendance);
    }

    private void validateAttendance(Attendance attendance) {
        studentRepository.findById(attendance.getStudentId()).orElseThrow(() ->
                new IllegalArgumentException("Student with id = " + attendance.getStudentId() + " does not exist"));
        trainingRepository.findById(attendance.getTrainingId()).orElseThrow(() ->
                new IllegalArgumentException("Training with id = " + attendance.getTrainingId() + " does not exist"));
    }

    public void delete(Long id) {
        attendanceRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Attendance with id = " + id + " does not exist"));
        attendanceRepository.deleteById(id);
    }
}
