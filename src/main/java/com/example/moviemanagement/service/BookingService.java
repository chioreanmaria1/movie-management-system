package com.example.moviemanagement.service;

import com.example.moviemanagement.model.Customer;
import com.example.moviemanagement.model.Movie;
import com.example.moviemanagement.model.Screening;
import com.example.moviemanagement.model.Ticket;
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
        Screening screening = screeningRepository.findById(ticket.getScreeningId());
        Customer customer = customerRepository.findById(ticket.getCustomerId());
        if (customerRepository.findById(ticket.getCustomerId()) == null){
            throw new IllegalArgumentException("Customer doesn't exist!");
        }
        if (screeningRepository.findById(ticket.getScreeningId()) == null){
            throw new IllegalArgumentException("Screening doesn't exist!");
        }
        if (seatRepository.findById(ticket.getSeatId()) == null || !(seatRepository.findById(ticket.getSeatId()).isAvailable())){
            throw new IllegalArgumentException("Seat doesn't exist!");
        }
        Movie movie = movieRepository.findById(screening.getMovieId());
        if (movie != null && !customer.canWatch(movie)){
            throw  new IllegalArgumentException("Customer is underaged and can't but this ticket");
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