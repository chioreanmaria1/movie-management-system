package com.example.moviemanagement.repository;

import com.example.moviemanagement.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository extends InMemoryRepository<Customer>{}