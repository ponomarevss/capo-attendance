package ru.sspo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sspo.model.Training;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}
