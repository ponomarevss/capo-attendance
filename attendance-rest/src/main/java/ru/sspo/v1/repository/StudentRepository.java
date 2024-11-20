package ru.sspo.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sspo.v1.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByGroupId(Long groupId);
}
