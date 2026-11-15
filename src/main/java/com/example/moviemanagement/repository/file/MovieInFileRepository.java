package com.example.moviemanagement.repository.file;

import com.example.moviemanagement.model.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
public class MovieInFileRepository extends InFileRepository<Movie> {
    public MovieInFileRepository(@Value("${repository.movies.file}") String path, ObjectMapper objectMapper){
        super(path, Movie.class, objectMapper);
    }
}
