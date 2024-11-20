package ru.sspo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "training_id")
    private Long trainingId;

    private String comment;
}