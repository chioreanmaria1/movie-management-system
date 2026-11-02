package com.example.moviemanagement.service;

import com.example.moviemanagement.model.Customer;
import com.example.moviemanagement.model.Screening;
import com.example.moviemanagement.model.Ticket;
import com.example.moviemanagement.repository.CustomerRepository;
import com.example.moviemanagement.repository.ScreeningRepository;
import com.example.moviemanagement.repository.SeatRepository;
import com.example.moviemanagement.repository.TicketRepository;
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


    public BookingService(CustomerRepository customerRepository, TicketRepository ticketRepository, ScreeningRepository screeningRepository, SeatRepository seatRepository) {
        this.customerRepository = customerRepository;
        this.ticketRepository = ticketRepository;
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
    }

    //buy ticket, cancel ticket, get tickets de la clienti, get ticket de la screening

    //buy ticket ----> salveaza biletul,
    ///trebuie verificat daca clientul poate cumpaar biletul
    public void saveTicket(Ticket ticket){
        if (customerRepository.findById(ticket.getCustomerId()) == null){
            throw new IllegalArgumentException("Customer doesn't exist!");
        }
        if (screeningRepository.findById(ticket.getScreeningId()) == null){
            throw new IllegalArgumentException("Screening doesn't exist!");
        }
        if (seatRepository.findById(ticket.getSeatId()) == null){
            throw new IllegalArgumentException("Seat doesn't exist!");
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

//    public List<Customer> findClientsFromATicket(String id){
//        List<Customer> customers = new ArrayList<>();
//        if (ticketRepository.findById(id) == null){
//            throw new IllegalArgumentException("Ticket doesn't exist!");
//        }
//        for (Customer customer: customerRepository.findAll()){
//            if(customer.)
//        }
//    }
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