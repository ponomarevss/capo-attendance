package ru.sspo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sspo.model.Attendance;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByTrainingId(Long trainingId);
}
