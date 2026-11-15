package com.example.moviemanagement.repository.memory;

import com.example.moviemanagement.model.Movie;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository extends InMemoryRepository<Movie> {

}
