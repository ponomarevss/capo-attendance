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
public class AttendanceControllerTest {

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    WebTestClient webTestClient;

    @AfterEach
    void afterEach() {
        studentRepository.deleteAll();
        trainingRepository.deleteAll();
        attendanceRepository.deleteAll();
    }

    @Test
    void testGetById() {
        // GET /attendance/{id}
        Student givenStudent = studentRepository.save(createGivenStudent());
        Training givenTraining = trainingRepository.save(createGivenTraining());
        Attendance givenAttendance = attendanceRepository.save(createGivenAttendance(givenStudent, givenTraining));

        webTestClient.get()
                .uri("/attendances/" + givenAttendance.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Attendance.class)
                .value(actual -> assertEquals(givenAttendance, actual));
    }

    @Test
    void testGetAll() {
        // GET /attendances

        Student givenStudent = studentRepository.save(createGivenStudent());
        Training givenTraining = trainingRepository.save(createGivenTraining());

        List<Attendance> givenAttendances = attendanceRepository.saveAll(List.of(
                createGivenAttendance(givenStudent, givenTraining),
                createGivenAttendance(givenStudent, givenTraining)));

        webTestClient.get()
                .uri("/attendances")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Attendance>>() {
                })
                .value(actual -> assertEquals(givenAttendances, actual));
    }

    @Test
    void testCreate() {
        // POST /attendances

        Student givenStudent = studentRepository.save(createGivenStudent());
        Training givenTraining = trainingRepository.save(createGivenTraining());
        Attendance givenAttendance = createGivenAttendance(givenStudent, givenTraining);

        webTestClient.post()
                .uri("/attendances")
                .body(BodyInserters.fromValue(givenAttendance))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Attendance.class)
                .value(actual -> {
                            assertNotNull(actual.getId());
                            assertEquals(givenAttendance.getStudentId(), actual.getStudentId());
                            assertEquals(givenAttendance.getTrainingId(), actual.getTrainingId());
                            assertEquals(givenAttendance.getComment(), actual.getComment());
                            assertTrue(attendanceRepository.existsById(actual.getId()));
                        });
    }

    @Test
    void testDelete() {
        // DELETE /attendances/{id}
        Student givenStudent = studentRepository.save(createGivenStudent());
        Training givenTraining = trainingRepository.save(createGivenTraining());
        Attendance givenAttendance = attendanceRepository.save(createGivenAttendance(givenStudent, givenTraining));

        webTestClient.delete()
                .uri("/attendances/" + givenAttendance.getId())
                .exchange()
                .expectStatus().isNoContent();
        assertFalse(attendanceRepository.existsById(givenAttendance.getId()));
    }
}
