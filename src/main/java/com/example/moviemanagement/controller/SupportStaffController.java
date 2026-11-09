package com.example.moviemanagement.controller;

import com.example.moviemanagement.model.Role;
import com.example.moviemanagement.model.SupportStaff;
import com.example.moviemanagement.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/supportstaff")
public class SupportStaffController {
    private final StaffService staffService;

    public SupportStaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public String findAllSupportStaff(Model model){
        model.addAttribute("supportStaff", staffService.findAllSupportStaff());
        return "supportstaff/index";
    }

    @GetMapping("/new")
    public String createSupportStaff(Model model){
        model.addAttribute("supportStaff", new SupportStaff());
        model.addAttribute("roles", Role.values());
        return "supportstaff/form";
    }

    @PostMapping
    public String saveSupportStaff(@ModelAttribute SupportStaff supportStaff){
        staffService.saveStaff(supportStaff);
        return "redirect:/supportstaff";
    }

    @PostMapping("{id}/delete")
    public String deleteSupportStaff(@PathVariable String id){
        staffService.deleteStaff(id);
        return "redirect:/supportstaff";
    }
}
