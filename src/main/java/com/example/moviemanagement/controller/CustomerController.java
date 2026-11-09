package com.example.moviemanagement.controller;

import com.example.moviemanagement.model.Customer;
import com.example.moviemanagement.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String findAllCustomers(Model model){
        model.addAttribute("customers", customerService.findAllCustomers());
        return "customer/index";
    }
    @GetMapping("/new")
    public String createCustomer(Model model){
        model.addAttribute("customer", new Customer());
        return "customer/form";
    }
    @PostMapping
    public String saveCustomer(@ModelAttribute Customer customer){
        customerService.saveCustomer(customer);
        return "redirect:/customers";
    }
    @PostMapping("{id}/delete")
    public String deleteCustomer(@PathVariable String id){
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}