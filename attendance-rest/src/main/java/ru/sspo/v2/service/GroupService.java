package ru.sspo.v2.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.sspo.aspect.logging.Logging;
import ru.sspo.v2.model.Group;
import ru.sspo.v2.model.Student;
import ru.sspo.v2.repository.GroupRepository;
import ru.sspo.v2.repository.StudentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Logging
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
        validateGroup(group.getName(), group.getDescription());
        return groupRepository.save(group);
    }

    private static void validateGroup(String name, String description) {
        if (isNullOrEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (isNullOrEmpty(description)) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
    }

    private static boolean isNullOrEmpty(String s) {
        return Objects.isNull(s) || s.isEmpty();
    }

    public void delete(Long id) {
        groupRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Group with id = " + id + " does not exist."));
        studentRepository.findByGroupId(id).forEach(student -> {
            student.setGroupId(null);
            studentRepository.save(student);
        });
        groupRepository.deleteById(id);
    }

    public List<Student> findStudents(Long id) {
        groupRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Group with id = " + id + " does not exist."));
        return studentRepository.findByGroupId(id);
    }
}
