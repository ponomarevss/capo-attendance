package ru.sspo.client;

import lombok.Data;

@Data
public class GroupResponse {
    private Long id;
    private String name;
    private String address;
    private String description;
}
