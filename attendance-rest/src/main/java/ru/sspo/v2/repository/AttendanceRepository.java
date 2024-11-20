package ru.sspo.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sspo.v2.model.Attendance;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByStudentId(Long studentId);
    List<Attendance> findByTrainingId(Long trainingId);
}
