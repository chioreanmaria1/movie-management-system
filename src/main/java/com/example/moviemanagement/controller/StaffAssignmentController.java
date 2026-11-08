package com.example.moviemanagement.controller;

import com.example.moviemanagement.model.StaffAssignment;
import com.example.moviemanagement.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staffassignments")
public class StaffAssignmentController {
    private final StaffService staffService;

    public StaffAssignmentController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public String findAllStaffAssignments(Model model){
        model.addAttribute("staffAssignments", staffService.findAllStaffAssignments());
        return "staffassignment/index";
    }

    @GetMapping("/new")
    public String createStaffAssignment(Model model){
        model.addAttribute("staffAssignment", new StaffAssignment());
        return "staffassignment/form";
    }

    @PostMapping
    public String saveStaffAssignment(@ModelAttribute StaffAssignment staffAssignment){
        staffService.saveStaffAssignment(staffAssignment);
        return "redirect:/staffassignments";
    }

    @PostMapping("{id}/delete")
    public String deleteStaffAssignment(@PathVariable String id){
        staffService.deleteStaffAssignment(id);
        return "redirect:/staffassignments";
    }
}
