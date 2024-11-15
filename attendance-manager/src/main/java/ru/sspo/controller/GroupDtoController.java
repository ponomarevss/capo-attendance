package ru.sspo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
        model.addAttribute("restStudents", allStudents);
        model.addAttribute("isStudentListEmpty", groupDto.getStudents().isEmpty());
        return "edit-group.html";
    }

    @GetMapping("/create")
    public String showCreateGroupForm(Model model) {
        model.addAttribute("group", new GroupDto());
        return "create-group.html";
    }

    @PostMapping("/update")
    public String updateGroup(@ModelAttribute GroupDto groupDto) {
        groupDtoService.save(GroupDto.toResponse(groupDto));
        return "redirect:/groups";
    }

    @PostMapping("/save")
    public String createGroup(@ModelAttribute GroupDto groupDto) {
        groupDtoService.save(GroupDto.toResponse(groupDto));
        return "redirect:/groups";
    }


    @PostMapping("/delete/{id}")
    public String deleteGroup(@PathVariable Long id) {
        groupDtoService.deleteById(id);
        return "redirect:/groups";
    }

    @PostMapping("/addStudent")
    public String addStudentToGroup(
            @RequestParam("studentId") Long studentId,
            @RequestParam("groupId") Long groupId,
            Model model
    ) {
        studentDtoService.addStudentToGroup(studentId, groupId);
        showEditGroupForm(groupId, model);
        return "redirect:edit/" + groupId;
    }

    @PostMapping("/removeStudent")
    public String removeStudentFromGroup(
            @RequestParam("studentId") Long studentId,
            @RequestParam("groupId") Long groupId,
            Model model
    ) {
        studentDtoService.removeStudentFromGroup(studentId);
        showEditGroupForm(groupId, model);
        return "redirect:edit/" + groupId;
    }
}
