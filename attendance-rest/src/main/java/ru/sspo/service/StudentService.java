package ru.sspo.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.sspo.model.Student;
import ru.sspo.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    public static final long YEARS_TO_SUBTRACT = 6L;
    private final StudentRepository studentRepository;

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student create(@NotNull Student student) {
        String firstname = student.getFirstname();
        String lastname = student.getLastname();
        LocalDate dateOfBirth = student.getBirthday();

        if (Objects.isNull(firstname) || firstname.isEmpty()) {
            throw new IllegalArgumentException("Firstname cannot be null or empty.");
        }
        if (Objects.isNull(lastname) || lastname.isEmpty()) {
            throw new IllegalArgumentException("Lastname cannot be null or empty.");
        }
        if (dateOfBirth.isAfter(LocalDate.now().minusYears(YEARS_TO_SUBTRACT))) {
            throw new IllegalArgumentException("Student cannot be so young.");
        }
        return studentRepository.save(student);
    }

    public void delete(Long id) {
        if (studentRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Student with id = " + id + " does not exist.");
        }
    }
}
