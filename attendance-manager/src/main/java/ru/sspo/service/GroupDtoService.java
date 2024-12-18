package ru.sspo.service;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import ru.sspo.client.GroupResponse;
import ru.sspo.client.StudentResponse;
import ru.sspo.dto.GroupDto;
import ru.sspo.dto.StudentDto;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GroupDtoService extends BaseDtoService{

    public GroupDtoService(DiscoveryClient discoveryClient) {
        super(discoveryClient);
    }

    public List<GroupDto> findAll() {
        return restClient().get()
                .uri("/groups")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public Optional<GroupDto> findById(Long id) {
        try {
            GroupResponse groupResponse = restClient().get()
                    .uri("groups/" + id)
                    .retrieve()
                    .body(GroupResponse.class);
            if (groupResponse == null) {
                return Optional.empty();
            }
            List<StudentDto> studentDtoList = getStudentsForGroup(groupResponse.getId())
                    .stream()
                    .map(studentResponse -> StudentDto.fromResponse(studentResponse, groupResponse))
                    .sorted(Comparator.comparing(StudentDto::getLastname))
                    .toList();
            return Optional.of(GroupDto.fromResponse(groupResponse, studentDtoList));
        } catch (HttpServerErrorException e) {
            return Optional.empty();
        }
    }

    private List<StudentResponse> getStudentsForGroup(Long id) {
        return restClient().get()
                .uri("groups/" + id + "/students")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public void save(GroupResponse group) {
        restClient().post()
                .uri("/groups")
                .body(group)
                .retrieve();
    }

    public void deleteById(Long id) {
        restClient().delete()
                .uri("/groups/" + id)
                .retrieve();
    }

}
