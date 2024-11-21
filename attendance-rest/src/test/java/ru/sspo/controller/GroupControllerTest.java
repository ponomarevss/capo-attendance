package ru.sspo.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.sspo.model.Group;
import ru.sspo.model.Student;
import ru.sspo.repository.GroupRepository;
import ru.sspo.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.sspo.controller.TestEntityFactory.createGivenGroup;
import static ru.sspo.controller.TestEntityFactory.createGivenStudent;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
public class GroupControllerTest {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    WebTestClient webTestClient;

    @AfterEach
    void afterEach() {
        studentRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    void testGetById() {
        // GET /groups/{id}
        Group givenGroup = groupRepository.save(createGivenGroup());

        webTestClient.get()
                .uri("/groups/" + givenGroup.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Group.class)
                .value(actual -> assertEquals(givenGroup, actual));
    }

    @Test
    void testGetAll() {
        // GET /groups
        List<Group> givenGroups = List.of(createGivenGroup(), createGivenGroup());
        groupRepository.saveAll(givenGroups);

        webTestClient.get()
                .uri("/groups")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Group>>() {
                })
                .value(actual -> assertEquals(givenGroups, actual));
    }

    @Test
    void testCreate() {
        // POST /groups
        Group givenGroup = createGivenGroup();

        webTestClient.post()
                .uri("/groups")
                .body(BodyInserters.fromValue(givenGroup))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Group.class)
                .value(actual -> {
                            assertNotNull(actual.getId());
                            assertEquals(givenGroup.getName(), actual.getName());
                            assertEquals(givenGroup.getAddress(), actual.getAddress());
                            assertEquals(givenGroup.getDescription(), actual.getDescription());
                            assertTrue(groupRepository.existsById(actual.getId()));
                        });
    }

    @Test
    void testDelete() {
        // DELETE /groups/{id}
        Group givenGroup = groupRepository.save(createGivenGroup());

        webTestClient.delete()
                .uri("/groups/" + givenGroup.getId())
                .exchange()
                .expectStatus().isNoContent();
        assertFalse(groupRepository.existsById(givenGroup.getId()));
    }

    @Test
    void testGetStudents() {
        // GET /groups/{id}/students
        Group givenGroup = groupRepository.save(createGivenGroup());

        Student givenStudent1 = createGivenStudent();
        Student givenStudent2 = createGivenStudent();
        givenStudent1.setGroupId(givenGroup.getId());
        givenStudent2.setGroupId(givenGroup.getId());
        List<Student> givenStudents = studentRepository.saveAll(List.of(givenStudent1, givenStudent2));

        webTestClient.get()
                .uri("/groups/" + givenGroup.getId() + "/students")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Student>>() {
                })
                .value(actual -> assertEquals(givenStudents, actual));
    }
}
