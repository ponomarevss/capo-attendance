package ru.sspo.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.sspo.model.Group;
import ru.sspo.model.Student;
import ru.sspo.repository.GroupRepository;
import ru.sspo.repository.StudentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public Optional<Group> findById(Long id) {
        return groupRepository.findById(id);
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group create(@NotNull Group group) {
        String name = group.getName();
        String description = group.getDescription();

        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (Objects.isNull(description) || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
        return groupRepository.save(group);
    }

    public void delete(Long id) {
        if (groupRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Group with id = " + id + " does not exist.");
        }
        groupRepository.deleteById(id);
    }

    public List<Student> findStudents(Long id) {
        if (groupRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Group with id = " + id + " does not exist.");
        }
        return studentRepository.findByGroupId(id);
    }
}
