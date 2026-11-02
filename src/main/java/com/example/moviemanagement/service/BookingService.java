package com.example.moviemanagement.service;

import com.example.moviemanagement.model.Customer;
import com.example.moviemanagement.model.Movie;
import com.example.moviemanagement.model.Screening;
import com.example.moviemanagement.model.Ticket;
import com.example.moviemanagement.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//Customer, Ticket, Screening

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

    ///trebuie verificat daca clientul poate cumpaar biletul
    public void saveTicket(Ticket ticket){
        Screening screening = screeningRepository.findById(ticket.getScreeningId());
        Movie movie = movieRepository.findById(screening.getMovieId());
        Customer customer = customerRepository.findById(ticket.getCustomerId());
        if (customerRepository.findById(ticket.getCustomerId()) == null){
            throw new IllegalArgumentException("Customer doesn't exist!");
        }
        if (screeningRepository.findById(ticket.getScreeningId()) == null){
            throw new IllegalArgumentException("Screening doesn't exist!");
        }
        if (seatRepository.findById(ticket.getSeatId()) == null){
            throw new IllegalArgumentException("Seat doesn't exist!");
        }
        if (movie != null && !customer.canWatch(movie)){
            throw  new IllegalArgumentException("Customer is underaged and can't but this ticket");
        }
        ticketRepository.save(ticket);
    }
    //ca daca stergi un ruicket, ar trebui si din lista de tickeet a clientului sa stergi
    //trebuie sa sterg doar daca un customer nu are
    public void deleteTicket(String id){
        if (ticketRepository.findById(id) == null){
            throw new IllegalArgumentException("You can't delete a ticket that doesnt exist!");
        }
        ticketRepository.delete(id);
    }

    //toate biletele unui screening
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