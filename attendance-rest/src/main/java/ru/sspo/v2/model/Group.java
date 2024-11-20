package ru.sspo.v2.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "training_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private String address;
    private String description;
}
