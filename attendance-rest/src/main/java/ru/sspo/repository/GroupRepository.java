package ru.sspo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sspo.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
