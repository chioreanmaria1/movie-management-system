package com.example.moviemanagement.repository;

import java.util.LinkedHashMap;
import java.util.List;


public abstract class InMemoryRepository<T extends HasId> implements BaseRepository<T>{

    LinkedHashMap<String, T> objects;

    public InMemoryRepository() {
        this.objects = new LinkedHashMap<>();
    }

    @Override
    public void save(T object) {
        this.objects.put(object.getId(), object);
    }

    @Override
    public List<T>findAll() {
        return null;
    }

    @Override
    public T findById(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
