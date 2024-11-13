package ru.sspo.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import ru.sspo.client.GroupResponse;
import ru.sspo.client.StudentResponse;
import ru.sspo.dto.StudentDto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentDtoService {

    private final RestClient restClient;

    public StudentDtoService() {
        restClient = RestClient.create("http://localhost:8010");
    }

    public List<StudentDto> findAll() {
        List<StudentResponse> studentResponses = restClient.get()
                .uri("/students")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        return Objects.requireNonNull(studentResponses).stream().map(studentResponse ->
                StudentDto.fromResponse(studentResponse, getGroupResponse(studentResponse))
        ).toList();
    }

    public Optional<StudentDto> findById(Long id) {
        try {
            StudentResponse studentResponse = restClient.get()
                    .uri("students/" + id)
                    .retrieve()
                    .body(StudentResponse.class);
            if (studentResponse == null) {
                return Optional.empty();
            }
            GroupResponse groupResponse = getGroupResponse(studentResponse);
            return Optional.of(StudentDto.fromResponse(studentResponse, groupResponse));
        } catch (HttpServerErrorException e) {
            return Optional.empty();
        }
    }

    private GroupResponse getGroupResponse(StudentResponse studentResponse) {
        if (studentResponse.getGroupId() != null) {
            return restClient.get()
                    .uri("groups/" + studentResponse.getGroupId())
                    .retrieve()
                    .body(GroupResponse.class);
        }
        return null;
    }

    public void save(StudentResponse student) {
//        System.out.println("save: " + student);
        restClient.post()
                .uri("/students")
                .body(student)
                .retrieve();
    }

    public void deleteById(Long id) {
        restClient.delete()
                .uri("/students/" + id)
                .retrieve();
    }
}
