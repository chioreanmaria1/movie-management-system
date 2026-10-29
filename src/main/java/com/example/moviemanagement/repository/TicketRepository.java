package com.example.moviemanagement.repository;

import com.example.moviemanagement.model.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository extends InMemoryRepository<Ticket>{}