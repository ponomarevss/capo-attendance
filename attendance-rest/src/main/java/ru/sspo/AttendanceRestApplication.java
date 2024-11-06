package ru.sspo;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.sspo.model.Student;
import ru.sspo.service.StudentService;

import java.time.LocalDate;
import java.time.ZoneId;

@SpringBootApplication
public class AttendanceRestApplication
{
//    public static void main( String[] args )
//    {
//        SpringApplication.run(AttendanceRestApplication.class, args);
//    }

    public static void main( String[] args )
    {
        ConfigurableApplicationContext context = SpringApplication.run(AttendanceRestApplication.class, args);

        StudentService studentService = context.getBean(StudentService.class);

        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setFirstname(faker.name().firstName());
            student.setLastname(faker.name().lastName());
            student.setBirthday(faker.date()
                    .birthday(7, 11)
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
            studentService.create(student);
        }
    }
}
