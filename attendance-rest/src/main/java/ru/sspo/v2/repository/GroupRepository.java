package ru.sspo.v2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sspo.v2.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
