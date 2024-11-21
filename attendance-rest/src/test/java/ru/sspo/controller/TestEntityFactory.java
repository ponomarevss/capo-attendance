package ru.sspo.controller;

import com.github.javafaker.Faker;
import ru.sspo.model.Attendance;
import ru.sspo.model.Group;
import ru.sspo.model.Student;
import ru.sspo.model.Training;

import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

public class TestEntityFactory {
    private static final Faker faker = new Faker();

    static Student createGivenStudent() {
        Student student = new Student();
        student.setFirstname(faker.name().firstName());
        student.setLastname(faker.name().lastName());
        student.setBirthday(faker.date()
                .birthday(7, 14)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate());
        return student;
    }

    static Group createGivenGroup() {
        Group group = new Group();
        group.setName(faker.team().name());
        group.setDescription(faker.lorem().sentence(3));
        group.setAddress(faker.address().streetAddress());
        return group;
    }

    static Training createGivenTraining() {
        Training training = new Training();
        training.setDatetime(faker.date().past(300, TimeUnit.DAYS)
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        );
        training.setStatus(faker.lorem().sentence(1));
        return training;
    }

    static Attendance createGivenAttendance(Student student, Training training) {
        Attendance attendance = new Attendance();
        attendance.setStudentId(student.getId());
        attendance.setTrainingId(training.getId());
        attendance.setComment(faker.lorem().sentence(3));
        return attendance;
    }
}
