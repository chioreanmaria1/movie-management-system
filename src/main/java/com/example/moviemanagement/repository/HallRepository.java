package com.example.moviemanagement.repository;

import com.example.moviemanagement.model.Hall;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HallRepository extends InMemoryRepository<Hall>{
    public List<Hall> findByTheatreId(String id){
        List<Hall> halls = new ArrayList<>();
        for(Hall hall: objects.values()){
            if(hall.getTheatreId().equals(id)){
                halls.add(hall);
            }
        }
        return halls;
    }
}