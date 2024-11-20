package ru.sspo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class AttendanceRestApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AttendanceRestApplication.class, args);

        // region Создание сущностей
//        Initializer.initializeStudents(context);
//        Initializer.initializeGroups(context);
        // endregion

        // region Распределение студентов по случайным группам
//        StudentController studentController = context.getBean(StudentController.class);
//        GroupController groupController = context.getBean(GroupController.class);
//        List<Student> students = studentController.getAll().getBody();
//        List<Group> groups = groupController.getAll().getBody();
//
//        students.forEach(student -> {
//                    student.setGroupId(groups.get(new Random().nextInt(groups.size())).getId());
//                    studentController.create(student);
//                }
//        );
        // endregion

        // region Удаление группы
//        GroupController groupController = context.getBean(GroupController.class);
//        List<Group> groups = groupController.getAll().getBody();
//        Group group = groups.get(new Random().nextInt(groups.size()));
//        groupController.delete(group.getId());
        // endregion
    }
}
