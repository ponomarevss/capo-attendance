package ru.sspo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sspo.aspect.logging.Logging;
import ru.sspo.model.Attendance;
import ru.sspo.model.Training;
import ru.sspo.repository.AttendanceRepository;
import ru.sspo.repository.TrainingRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Logging
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final AttendanceRepository attendanceRepository;

    public Optional<Training> findById(Long id) {
        return trainingRepository.findById(id);
    }

    public List<Training> findAll() {
        return trainingRepository.findAll();
    }

    public Training create(Training training) {
        if (Objects.isNull(training.getDatetime())) {
            throw new IllegalArgumentException("DateTime is null.");
        }
        return trainingRepository.save(training);
    }

    public void delete(Long id) {
        checkTrainingExists(id);
        attendanceRepository.findByTrainingId(id).forEach(attendance ->
                attendanceRepository.deleteById(attendance.getId()));
        trainingRepository.deleteById(id);
    }

    private void checkTrainingExists(Long id) {
        trainingRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("Training with id = " + id + " does not exist."));
    }

    public List<Attendance> findAttendances(Long id) {
        checkTrainingExists(id);
        return attendanceRepository.findByTrainingId(id);
    }
}
