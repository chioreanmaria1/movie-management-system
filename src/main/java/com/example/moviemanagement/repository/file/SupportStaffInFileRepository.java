package com.example.moviemanagement.repository.file;

import com.example.moviemanagement.model.SupportStaff;
import com.example.moviemanagement.repository.file.InFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
public class SupportStaffInFileRepository extends InFileRepository<SupportStaff> {
    public SupportStaffInFileRepository(@Value("${repository.support-staff.file}") String path, ObjectMapper objectMapper) {
        super(path, SupportStaff.class, objectMapper);
    }
}