package com.example.moviemanagement.repository.file;

import com.example.moviemanagement.model.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
public class TicketInFileRepository extends InFileRepository<Ticket>{

    public TicketInFileRepository(@Value ("${repository.tickets.file}")String path, ObjectMapper objectMapper) {
        super(path, Ticket.class, objectMapper);
    }
}