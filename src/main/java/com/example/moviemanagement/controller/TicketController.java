package com.example.moviemanagement.controller;


import com.example.moviemanagement.model.Ticket;
import com.example.moviemanagement.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController{
    private final BookingService bookingService;

    public TicketController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String findAllTickets(Model model){
        model.addAttribute("tickets",bookingService.findAllTickets());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String createTicket(Model model){
        model.addAttribute("ticket", new Ticket());
        return "ticket/form";
    }

    @PostMapping
    public String saveTicket(@ModelAttribute Ticket ticket){
        bookingService.saveTicket(ticket);
        return "redirect:/tickets";
    }
    @PostMapping("{id}/delete")
    public String deleteTicket(@PathVariable String id){
        bookingService.deleteTicket(id);
        return "redirect:/tickets";
    }

}
