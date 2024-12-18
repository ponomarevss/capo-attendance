package ru.sspo.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.sspo.aspect.logging.Logging;
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
        validateGroup(group);
        return groupRepository.save(group);
    }

    private static void validateGroup(Group group) {
        if (isNullOrEmpty(group.getName())) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (isNullOrEmpty(group.getDescription())) {
            throw new IllegalArgumentException("Description cannot be null or empty.");
        }
    }

    private static boolean isNullOrEmpty(String s) {
        return Objects.isNull(s) || s.isEmpty();
    }

    public void delete(Long id) {
        checkGroupExists(id);
        studentRepository.findByGroupId(id).forEach(student -> {
            student.setGroupId(null);
            studentRepository.save(student);
        });
        groupRepository.deleteById(id);
    }

    public List<Student> findStudents(Long id) {
        checkGroupExists(id);
        return studentRepository.findByGroupId(id);
    }

    private void checkGroupExists(Long id) {
        groupRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Group with id = " + id + " does not exist."));
    }
}
