package com.example.moviemanagement.controller;

import com.example.moviemanagement.model.Specialization;
import com.example.moviemanagement.model.TechnicalOperator;
import com.example.moviemanagement.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/technicaloperators")
public class TechnicalOperatorController {
    private final StaffService staffService;

    public TechnicalOperatorController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public String findAllTechnicalOperators(Model model){
        model.addAttribute("technicalOperators", staffService.findAllTechnicalOperator());
        return "technicaloperator/index";
    }

    @GetMapping("/new")
    public String createTechnicalOperator(Model model){
        model.addAttribute("technicalOperator", new TechnicalOperator());
        model.addAttribute("specializations", Specialization.values());
        return "technicaloperator/form";
    }

    @PostMapping
    public String saveTechnicalOperator(@ModelAttribute TechnicalOperator technicalOperator){
        staffService.saveStaff(technicalOperator);
        return "redirect:/technicaloperators";
    }

    @PostMapping("{id}/delete")
    public String deleteTechnicalOperator(@PathVariable String id){
        staffService.deleteStaff(id);
        return "redirect:/technicaloperators";
    }
}
