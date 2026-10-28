package com.example.moviemanagement.repository;

import java.util.List;

public interface BaseRepository<T> {
    void save(T object);
    //return a list of the objects
    List<T> findAll();
    T findById(String id);
    void delete(String id);
}
