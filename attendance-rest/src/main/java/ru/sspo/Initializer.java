package ru.sspo;

import com.github.javafaker.Faker;
import org.springframework.context.ConfigurableApplicationContext;
import ru.sspo.model.Group;
import ru.sspo.model.Student;
import ru.sspo.service.GroupService;
import ru.sspo.service.StudentService;

import java.time.ZoneId;

public class Initializer {

    private static final Faker faker = new Faker();

    public static void initializeStudents(ConfigurableApplicationContext context) {
        StudentService studentService = context.getBean(StudentService.class);

        for (int i = 0; i < 6; i++) {
            Student student = new Student();
            student.setFirstname(faker.name().firstName());
            student.setLastname(faker.name().lastName());
            student.setBirthday(faker.date()
                    .birthday(7, 14)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            studentService.create(student);
        }
    }

    public static void initializeGroups(ConfigurableApplicationContext context) {
        GroupService groupService = context.getBean(GroupService.class);
        for (int i = 0; i < 2; i++) {
            Group group = new Group();
            group.setName(faker.team().name());
            group.setDescription(faker.lorem().sentence(3));
            group.setAddress(faker.address().streetAddress());
            groupService.create(group);
        }
    }
}
