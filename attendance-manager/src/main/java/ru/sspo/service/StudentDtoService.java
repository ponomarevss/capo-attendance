package ru.sspo.service;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import ru.sspo.client.GroupResponse;
import ru.sspo.client.StudentResponse;
import ru.sspo.dto.StudentDto;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class StudentDtoService extends BaseDtoService{

    public StudentDtoService(DiscoveryClient discoveryClient) {
        super(discoveryClient);
    }

    public List<StudentDto> findAll() {
        try {
            List<StudentResponse> studentResponseList = restClient().get()
                    .uri("/students")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            if (studentResponseList == null) {
                throw new RuntimeException("Something went wrong...");
            }
            return studentResponseList.stream().map(studentResponse ->
                            StudentDto.fromResponse(studentResponse, findGroupResponse(studentResponse))
                    )
                    .sorted(Comparator.comparing(StudentDto::getGroupName, Comparator.nullsLast(String::compareTo))
                            .thenComparing(StudentDto::getLastname))
                    .toList();
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Something went wrong...");
        }
    }

    private Optional<StudentResponse> findResponseById(Long id) {
        try {
            StudentResponse studentResponse = restClient().get()
                    .uri("students/" + id)
                    .retrieve()
                    .body(StudentResponse.class);
            if (studentResponse == null) {
                return Optional.empty();
            }
            return Optional.of(studentResponse);
        } catch (HttpServerErrorException e) {
            return Optional.empty();
        }
    }

    public Optional<StudentDto> getDtoById(Long id) {
        if (findResponseById(id).isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(
                StudentDto.fromResponse(findResponseById(id).get(), findGroupResponse(findResponseById(id).get()))
        );
    }

    private GroupResponse findGroupResponse(StudentResponse studentResponse) {
        if (studentResponse.getGroupId() != null) {
            return restClient().get()
                    .uri("groups/" + studentResponse.getGroupId())
                    .retrieve()
                    .body(GroupResponse.class);
        }
        return null;
    }

    public void save(StudentResponse student) {
        restClient().post()
                .uri("/students")
                .body(student)
                .retrieve();
    }

    public void deleteById(Long id) {
        restClient().delete()
                .uri("/students/" + id)
                .retrieve();
    }

    public void addStudentToGroup(Long studentId, Long groupId) {
        Optional<StudentResponse> responseOptional = findResponseById(studentId);
        if (responseOptional.isEmpty()) {
            throw new RuntimeException("Something went wrong...");
        }
        StudentResponse studentResponse = responseOptional.get();
        studentResponse.setGroupId(groupId);
        save(studentResponse);
    }

    public void removeStudentFromGroup(Long id) {
        Optional<StudentResponse> responseOptional = findResponseById(id);
        if (responseOptional.isEmpty()) {
            throw new RuntimeException("Something went wrong...");
        }
        StudentResponse studentResponse = responseOptional.get();
        studentResponse.setGroupId(null);
        save(studentResponse);
    }
}
