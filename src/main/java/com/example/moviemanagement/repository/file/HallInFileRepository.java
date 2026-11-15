package com.example.moviemanagement.repository.file;

import com.example.moviemanagement.model.Hall;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
public class HallInFileRepository extends InFileRepository<Hall> {
    public HallInFileRepository(@Value("${repository.halls.file}") String path, ObjectMapper objectMapper){
        super(path, Hall.class, objectMapper);
    }
}
