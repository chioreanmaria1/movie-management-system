package com.example.moviemanagement.repository;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class InFileRepository<T extends HasId> implements BaseRepository<T>{
    private final File file;
    private final ObjectMapper objectMapper;
    private final JavaType listType;

    public InFileRepository(String path, Class<T> type,ObjectMapper objectMapper) {
        this.file = new File(path);
        this.objectMapper = objectMapper;
        this.listType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);  //construieste tipul "colectie de tip List" cu elemente de tip type (de ex Theatre)
    }

    @Override
    public List<T> findAll(){
        if(!file.exists() || file.length() == 0){
            return new ArrayList<>();
        }
        try{
            return objectMapper.readValue(file, listType);
        }
        catch (JacksonException e){
            throw new RuntimeException("Could not read the items from file", e);
        }
    }

    @Override
    public T findById(String id){
        for(T item: findAll()){
            if(item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    @Override
    public void save(T item){
        if ((item.getId()).isEmpty()) {
            item.setId(UUID.randomUUID().toString());
        }
        List<T> items = findAll();
        items.removeIf(existing->existing.getId().equals(item.getId()));
        items.add(item);
        writeInFile(items);
    }

    @Override
    public void delete(String id){
        List<T> items = findAll();
        items.removeIf(existing -> existing.getId().equals(id));
        writeInFile(items);
    }

    private void writeInFile(List<T> items){
        try{
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, items);
        }catch (JacksonException e){
            throw new RuntimeException("Could not save items to file", e);
        }
    }
}