package com.example.moviemanagement.repository.file;

import com.example.moviemanagement.model.Seat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
public class SeatInFileRepository extends InFileRepository<Seat>{
    public SeatInFileRepository(@Value("${repository.seats.file}") String path, ObjectMapper objectMapper){
        super(path, Seat.class, objectMapper);
    }
}