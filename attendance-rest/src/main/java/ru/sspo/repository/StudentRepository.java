package ru.sspo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sspo.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
