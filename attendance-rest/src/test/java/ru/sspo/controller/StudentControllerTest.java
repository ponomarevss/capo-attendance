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
public class StudentControllerTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TrainingRepository trainingRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

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
        // GET /students/{id}
        Student givenStudent = studentRepository.save(createGivenStudent());

        webTestClient.get()
                .uri("/students/" + givenStudent.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Student.class)
                .value(actual -> assertEquals(givenStudent, actual));
    }

    @Test
    void testGetAll() {
        // GET /students
        List<Student> givenStudents = List.of(createGivenStudent(), createGivenStudent());
        studentRepository.saveAll(givenStudents);

        webTestClient.get()
                .uri("/students")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Student>>() {
                })
                .value(actual -> assertEquals(givenStudents, actual));
    }

    @Test
    void testCreate() {
        // POST /students
        Student givenStudent = createGivenStudent();

        webTestClient.post()
                .uri("/students")
                .body(BodyInserters.fromValue(givenStudent))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Student.class)
                .value(actual -> {
                            assertNotNull(actual.getId());
                            assertEquals(givenStudent.getFirstname(), actual.getFirstname());
                            assertEquals(givenStudent.getLastname(), actual.getLastname());
                            assertEquals(givenStudent.getBirthday(), actual.getBirthday());
                            assertTrue(studentRepository.existsById(actual.getId()));
                        });
    }

    @Test
    void testDelete() {
        // DELETE /students/{id}
        Student givenStudent = studentRepository.save(createGivenStudent());

        webTestClient.delete()
                .uri("/students/" + givenStudent.getId())
                .exchange()
                .expectStatus().isNoContent();
        assertFalse(studentRepository.existsById(givenStudent.getId()));
    }

    @Test
    void testGetAttendances() {
        // GET /students/{id}/attendances
        Student givenStudent = studentRepository.save(createGivenStudent());
        Training givenTraining = trainingRepository.save(createGivenTraining());
        List<Attendance> givenAttendances = List.of(
                createGivenAttendance(givenStudent, givenTraining),
                createGivenAttendance(givenStudent, givenTraining)
        );
        attendanceRepository.saveAll(givenAttendances);

        webTestClient.get()
                .uri("/students/" + givenStudent.getId() + "/attendances")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<Attendance>>() {
                })
                .value(actual -> assertEquals(givenAttendances, actual));
    }
}
