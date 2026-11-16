package com.example.moviemanagement.repository.file;

import com.example.moviemanagement.model.TechnicalOperator;
import com.example.moviemanagement.repository.file.InFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
public class TechnicalOperatorInFileRepository extends InFileRepository<TechnicalOperator> {
    public TechnicalOperatorInFileRepository(@Value("${repository.technical-operators.file}") String path, ObjectMapper objectMapper) {
        super(path,TechnicalOperator.class, objectMapper);
    }
}