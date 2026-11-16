package com.example.moviemanagement.repository.file;

import com.example.moviemanagement.model.StaffAssignment;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;


@Repository
public class StaffAssignmentsInFileRepository extends InFileRepository<StaffAssignment> {
    public StaffAssignmentsInFileRepository(@Value ("${repository.staff-assignments.file}") String path, ObjectMapper objectMapper) {
        super(path, StaffAssignment.class, objectMapper);
    }
}