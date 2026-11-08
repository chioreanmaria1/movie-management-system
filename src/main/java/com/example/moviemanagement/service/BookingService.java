package com.example.moviemanagement.service;

import com.example.moviemanagement.model.*;
import com.example.moviemanagement.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final MovieRepository movieRepository;


    public BookingService(CustomerRepository customerRepository, TicketRepository ticketRepository, ScreeningRepository screeningRepository, SeatRepository seatRepository, MovieRepository movieRepository) {
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
        this.movieRepository = movieRepository;
    }
    public void saveTicket(Ticket ticket){
        //trebuie sa verific daca seat ul este available, daca scaunul apartine salii
        Screening screening = screeningRepository.findById(ticket.getScreeningId());
        Customer customer = customerRepository.findById(ticket.getCustomerId());
        Seat seat = seatRepository.findById(ticket.getSeatId());
        List<Ticket> screeningTickets = getTicketsByScreening(ticket.getScreeningId());
        if (customer == null){
            throw new IllegalArgumentException("Customer doesn't exist!");
        }
        if (screening == null){
            throw new IllegalArgumentException("Screening doesn't exist!");
        }
        if (seat == null || seat.isAvailable()){
            throw new IllegalArgumentException("Seat doesn't exist or it is unavailable!");
        }
        Movie movie = movieRepository.findById(screening.getMovieId());
        if (movie != null && !customer.canWatch(movie)){
            throw  new IllegalArgumentException("Customer is underaged and can't but this ticket");
        }
        for (Ticket ticket1: screeningTickets){
            if (ticket1.getSeatId().equals(ticket.getSeatId())){
                throw new IllegalArgumentException("Can't book a ticket for a seat that is already taken!");
            }
        }
        if (!(seat.getHallId().equals(screening.getHallId()))){
            throw new IllegalArgumentException("Seat doesn't belong to this hall!");
        }

        ticketRepository.save(ticket);
    }
    public void deleteTicket(String id){
        if (ticketRepository.findById(id) == null){
            throw new IllegalArgumentException("You can't delete a ticket that doesnt exist!");
        }
        ticketRepository.delete(id);
    }
    public List<Ticket> getTicketsByScreening(String screeningId){
        List <Ticket> screeningTickets = new ArrayList<>();
        for (Ticket ticket: ticketRepository.findAll()){
            if (ticket.getScreeningId().equals(screeningId)){
                screeningTickets.add(ticket);
            }
        }
        return screeningTickets;
    }

    public Ticket findTicketById(String id){
        return ticketRepository.findById(id);
    }

    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }



}