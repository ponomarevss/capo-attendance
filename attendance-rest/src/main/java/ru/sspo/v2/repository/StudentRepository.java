package ru.sspo.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sspo.v2.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByGroupId(Long groupId);
}
