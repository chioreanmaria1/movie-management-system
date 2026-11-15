package com.example.moviemanagement.repository.file;

import com.example.moviemanagement.model.Screening;
import com.example.moviemanagement.model.Seat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
public class ScreeningInFileRepository extends InFileRepository<Screening>{
    public ScreeningInFileRepository(@Value("${repository.screenings.file}") String path, ObjectMapper objectMapper){
        super(path, Screening.class, objectMapper);
    }
}
