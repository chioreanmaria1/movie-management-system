package com.example.moviemanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleBusinessErrors(IllegalArgumentException illegalArgumentException, Model model) {
        model.addAttribute("errorTitle", "Invalid data or action is not allowed!");
        model.addAttribute("errorMessage", illegalArgumentException.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleOtherExceptions(Exception ex, Model model){
        model.addAttribute("errorTitle", "Unexpected Error!");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
