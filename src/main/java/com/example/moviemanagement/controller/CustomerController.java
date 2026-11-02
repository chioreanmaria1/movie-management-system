package com.example.moviemanagement.controller;

import com.example.moviemanagement.model.Customer;
import com.example.moviemanagement.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> findAllCustomers(){
        return customerService.findAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer findCustomerById(@PathVariable String id){
        return customerService.findCustomerById(id);
    }

    @PostMapping
    public void saveCustomer(@RequestBody Customer customer){  //RequestBody obiectul ce l trimitem severului
        customerService.saveCustomer(customer);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable String id){
        customerService.deleteCustomer(id);
    }

    @GetMapping("/{id}/tickets/count")
    public int ticketsOfCustomer(@PathVariable String id) {
        return customerService.ticketsOfCustomer(id);
    }

    @GetMapping("/withMinTickets")
    public List<Customer> findCustomersWithMinTickets(@RequestParam int min) {
        return customerService.findCustomersWithMinTickets(min);
    }
}