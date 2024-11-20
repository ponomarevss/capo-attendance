package ru.sspo.v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sspo.v1.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
