package com.example.moviemanagement.repository.file;

import com.example.moviemanagement.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
public class CustomerInFileRepository extends InFileRepository<Customer>{
    public CustomerInFileRepository(@Value ("${repository.customers.file}") String path, ObjectMapper objectMapper) {
        super(path, Customer.class, objectMapper);
    }
}