package ru.sspo.v2.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private String grade;

    @Column(name = "group_id")
    private Long groupId;
}