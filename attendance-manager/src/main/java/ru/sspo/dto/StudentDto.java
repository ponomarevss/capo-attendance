package ru.sspo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.sspo.client.GroupResponse;
import ru.sspo.client.StudentResponse;

import java.time.LocalDate;
import java.util.Comparator;

@Data
public class StudentDto {
    private Long id;
    private String firstname;
    private String lastname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String grade;
    private Long groupId;
    private String groupName;

    public static StudentDto fromResponse(StudentResponse studentResponse, GroupResponse groupResponse) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(studentResponse.getId());
        studentDto.setFirstname(studentResponse.getFirstname());
        studentDto.setLastname(studentResponse.getLastname());
        studentDto.setBirthday(studentResponse.getBirthday());
        studentDto.setGrade(studentResponse.getGrade());
        studentDto.setGroupId(studentResponse.getGroupId());
        if (groupResponse != null) {
            studentDto.setGroupName(groupResponse.getName());
        }
        return studentDto;
    }
}
