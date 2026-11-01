package com.example.moviemanagement.repository;

import com.example.moviemanagement.model.Screening;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ScreeningRepository extends InMemoryRepository<Screening> {
    public List<Screening> findByHallId(String hallId){
        List<Screening> screenings = new ArrayList<>();
        for(Screening screening: objects.values()){
            if(screening.getHallId().equals(hallId)){
                screenings.add(screening);
            }
        }
        return screenings;
    }

    public List<Screening> findByMovieId(String movieId){
        List<Screening> screenings = new ArrayList<>();
        for(Screening screening: objects.values()){
            if(screening.getMovieId().equals(movieId)){
                screenings.add(screening);
            }
        }
        return screenings;
    }
}
