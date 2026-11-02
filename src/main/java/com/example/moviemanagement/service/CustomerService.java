package com.example.moviemanagement.service;
import com.example.moviemanagement.model.Customer;
import com.example.moviemanagement.repository.CustomerRepository;
import com.example.moviemanagement.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Customer, Tickets
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;

    public CustomerService(CustomerRepository customerRepository, TicketRepository ticketRepository) {
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
    }

    public void saveCustomer(Customer customer){
        if(customer.getName() == null ){
            throw new IllegalArgumentException("Customer's name cannot be empty!");
        }
        customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer findCustomerById(String id){
        return customerRepository.findById(id);
    }

    public void deleteCustomer(String id){
        if(findCustomerById(id) == null){
            throw new IllegalArgumentException("You can't delete a customer that does not exist!");
        }
        if(!ticketRepository.findByCustomerId(id).isEmpty()){
            throw new IllegalArgumentException("You can't delete customer with id "+id+" because it has tickets");
        }
        customerRepository.delete(id);
    }

    public int ticketsOfCustomer(String customerId){
        return ticketRepository.findByCustomerId(customerId).size();
    }

    public List<Customer> findCustomersWithMinTickets(int minimum){
        if (minimum < 0) {
            throw new IllegalArgumentException("Minimum number of tickets cannot be negative!");
        }
        List<Customer> result = new ArrayList<>();
        for(Customer customer: customerRepository.findAll()){
            if(ticketsOfCustomer(customer.getId())>=minimum){
                result.add(customer);
            }
        }
        return result;
    }
}