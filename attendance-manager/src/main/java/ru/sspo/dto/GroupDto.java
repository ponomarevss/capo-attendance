package ru.sspo.dto;

import lombok.Data;
import ru.sspo.client.GroupResponse;

import java.util.List;

@Data
public class GroupDto {
    private Long id;
    private String name;
    private String address;
    private String description;
    private List<StudentDto> students;

    public static GroupDto fromResponse(GroupResponse groupResponse, List<StudentDto> studentDtoList) {
        GroupDto groupDto = new GroupDto();
        groupDto.setId(groupResponse.getId());
        groupDto.setName(groupResponse.getName());
        groupDto.setAddress(groupResponse.getAddress());
        groupDto.setDescription(groupResponse.getDescription());
        groupDto.setStudents(studentDtoList);
        return groupDto;
    }
}
