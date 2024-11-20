package ru.sspo.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sspo.v2.model.Training;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}
