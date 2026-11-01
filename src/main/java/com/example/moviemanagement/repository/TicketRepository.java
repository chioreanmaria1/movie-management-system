package com.example.moviemanagement.repository;

import com.example.moviemanagement.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketRepository extends InMemoryRepository<Ticket>{
    public List<Ticket> findByCustomerId(String customerId){
        List<Ticket> result = new ArrayList<>();
        for(Ticket ticket: objects.values()){
            if(ticket.getCustomerId().equals(customerId)){
                result.add(ticket);
            }
        }
        return result;
    }
}