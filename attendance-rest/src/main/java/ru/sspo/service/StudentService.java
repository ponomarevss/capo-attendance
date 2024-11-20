package ru.sspo.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.sspo.aspect.logging.Logging;
import ru.sspo.model.Attendance;
import ru.sspo.model.Student;
import ru.sspo.repository.AttendanceRepository;
import ru.sspo.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Logging
public class StudentService {

    public static final long YEARS_TO_SUBTRACT = 6L;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student create(@NotNull Student student) {
        validateStudent(student.getFirstname(), student.getLastname(), student.getBirthday());
        return studentRepository.save(student);
    }

    private static void validateStudent(String firstname, String lastname, LocalDate dateOfBirth) {
        if (isNullOrEmpty(firstname)) {
            throw new IllegalArgumentException("Firstname cannot be null or empty.");
        }
        if (isNullOrEmpty(lastname)) {
            throw new IllegalArgumentException("Lastname cannot be null or empty.");
        }
        if (dateOfBirth.isAfter(LocalDate.now().minusYears(YEARS_TO_SUBTRACT))) {
            throw new IllegalArgumentException("Student cannot be so young.");
        }
    }

    private static boolean isNullOrEmpty(String s) {
        return Objects.isNull(s) || s.isEmpty();
    }

    public void delete(Long id) {
        checkStudentExists(id);
        attendanceRepository.findByStudentId(id).forEach(attendance ->
                attendanceRepository.deleteById(attendance.getId()));
        studentRepository.deleteById(id);
    }

    private void checkStudentExists(Long id) {
        studentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Student with id = " + id + " does not exist."));
    }

    public List<Attendance> findAttendances(Long id) {
        checkStudentExists(id);
        return attendanceRepository.findByStudentId(id);
    }
}
