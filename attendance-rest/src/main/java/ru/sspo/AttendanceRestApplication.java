package ru.sspo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AttendanceRestApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AttendanceRestApplication.class, args);

//        Initializer.initializeStudents(context);
//        Initializer.initializeGroups(context);

//      так переписывается студент, ему добавляется группа
//        StudentController controller = context.getBean(StudentController.class);
//        Student student = controller.getById(66L).getBody();
//        student.setGroupId(2L);
//        controller.create(student);
    }
}
