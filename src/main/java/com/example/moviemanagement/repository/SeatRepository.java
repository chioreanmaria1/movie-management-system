package com.example.moviemanagement.repository;

import com.example.moviemanagement.model.Seat;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SeatRepository extends InMemoryRepository<Seat>{
    public List<Seat> findByHallId(String hallId){
        List<Seat> seats = new ArrayList<>();
        for(Seat seat: objects.values()){
            if(seat.getHallId().equals(hallId)){
                seats.add(seat);
            }
        }
        return seats;
    }
}