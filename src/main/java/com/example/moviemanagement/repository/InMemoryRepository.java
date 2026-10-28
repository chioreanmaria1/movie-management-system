package com.example.moviemanagement.repository;

import java.util.ArrayList;
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
        if (objects.isEmpty()){
            System.out.println("There are no objects yet");
        }
        List<T> allObjects = new ArrayList<>();
        for (T object: this.objects.values()){
            allObjects.add(object);
        }
        return allObjects;
    }

    @Override
    public T findById(String id) {
        if(objects.containsKey(id)){
            return objects.get(id);
        }
        return null;
    }

    @Override
    public void delete(String id) {
        objects.remove(id);
    }
}