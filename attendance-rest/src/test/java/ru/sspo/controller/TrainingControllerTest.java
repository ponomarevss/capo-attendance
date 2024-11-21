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
import ru.sspo.model.Attendance;
import ru.sspo.model.Student;
import ru.sspo.model.Training;
import ru.sspo.repository.AttendanceRepository;
import ru.sspo.repository.StudentRepository;
import ru.sspo.repository.TrainingRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.sspo.controller.TestEntityFactory.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
public class TrainingControllerTest {

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    WebTestClient webTestClient;

    @AfterEach
    void afterEach() {
        trainingRepository.deleteAll();
        studentRepository.deleteAll();
        attendanceRepository.deleteAll();
    }

    @Test
    void testGetById() {
        // GET /trainings/{id}
        Training givenTraining = trainingRepository.save(createGivenTraining());

        webTestClient.get()
                .uri("/trainings/" + givenTraining.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Training.class)
                .value(actual -> assertEquals(givenTraining, actual));
    }

    @Test
    void testGetAll() {
        // GET /trainings
        List<Training> givenTrainings = List.of(createGivenTraining(), createGivenTraining());
        trainingRepository.saveAll(givenTrainings);

        webTestClient.get()
                .uri("/trainings")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Training>>() {
                })
                .value(actual -> assertEquals(givenTrainings, actual));
    }

    @Test
    void testCreate() {
        // POST /trainings
        Training givenTraining = createGivenTraining();

        webTestClient.post()
                .uri("/trainings")
                .body(BodyInserters.fromValue(givenTraining))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Training.class)
                .value(actual -> {
                            assertNotNull(actual.getId());
                            assertEquals(givenTraining.getDatetime(), actual.getDatetime());
                            assertEquals(givenTraining.getStatus(), actual.getStatus());
                            assertTrue(trainingRepository.existsById(actual.getId()));
                        });
    }

    @Test
    void testDelete() {
        // DELETE /trainings/{id}
        Training givenTraining = trainingRepository.save(createGivenTraining());

        webTestClient.delete()
                .uri("/trainings/" + givenTraining.getId())
                .exchange()
                .expectStatus().isNoContent();
        assertFalse(trainingRepository.existsById(givenTraining.getId()));
    }

    @Test
    void testGetAttendances() {
        // GET /trainings/{id}/attendances
        Training givenTraining = trainingRepository.save(createGivenTraining());
        Student givenStudent = studentRepository.save(createGivenStudent());
        List<Attendance> givenAttendances = attendanceRepository.saveAll(List.of(
                createGivenAttendance(givenStudent, givenTraining),
                createGivenAttendance(givenStudent, givenTraining)
        ));

        webTestClient.get()
                .uri("/trainings/" + givenTraining.getId() + "/attendances")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Attendance>>() {
                })
                .value(actual -> assertEquals(givenAttendances, actual));
    }
}
