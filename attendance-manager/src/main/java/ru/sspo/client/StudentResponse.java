package ru.sspo.client;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String grade;
    private Long groupId;
}
