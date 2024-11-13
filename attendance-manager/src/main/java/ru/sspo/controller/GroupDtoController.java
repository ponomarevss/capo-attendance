package ru.sspo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sspo.dto.GroupDto;
import ru.sspo.dto.StudentDto;
import ru.sspo.service.GroupDtoService;
import ru.sspo.service.StudentDtoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupDtoController {

    private final GroupDtoService groupDtoService;
    private final StudentDtoService studentDtoService;

    @GetMapping
    public String getGroupsListPage(Model model) {
        model.addAttribute("groups", groupDtoService.findAll());
        return "groups-list-page.html";
    }

    @GetMapping("/{id}")
    public String getGroupPage(@PathVariable Long id, Model model) {
        Optional<GroupDto> optionalGroup = groupDtoService.findById(id);
        if (optionalGroup.isEmpty()) {
            return "not-found.html";
        }
        model.addAttribute("group", optionalGroup.get());
        return "group-page.html";
    }

    @GetMapping("/edit/{id}")
    public String showEditGroupForm(@PathVariable Long id, Model model) {
        Optional<GroupDto> optionalGroup = groupDtoService.findById(id);
        if (optionalGroup.isEmpty()) {
            return "not-found.html";
        }
        GroupDto groupDto = optionalGroup.get();
        List<StudentDto> allStudents = new ArrayList<>(studentDtoService.findAll());
        allStudents.removeAll(groupDto.getStudents());
        model.addAttribute("group", groupDto);
        model.addAttribute("allStudents", allStudents);
        return "edit-group.html";
    }

}
