package com.example.moviemanagement.repository;

import com.example.moviemanagement.model.Theatre;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import tools.jackson.databind.ObjectMapper;
@Repository
public class TheatreInFileRepository extends InFileRepository<Theatre>{
    public TheatreInFileRepository(@Value("${repository.theatres.file}") String path, ObjectMapper objectMapper) {
        super(path, Theatre.class, objectMapper);
    }
}
