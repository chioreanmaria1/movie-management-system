package com.example.moviemanagement.service;

import com.example.moviemanagement.model.Screening;
import com.example.moviemanagement.model.Staff;
import com.example.moviemanagement.model.StaffAssignment;
import com.example.moviemanagement.repository.ScreeningRepository;
import com.example.moviemanagement.repository.StaffAssignmentRepository;
import com.example.moviemanagement.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Staff, StaffAssignment, Screening

@Service
public class StaffService {
    public final StaffRepository staffRepository;
    public final StaffAssignmentRepository staffAssignmentRepository;
    public final ScreeningRepository screeningRepository;

    public StaffService(StaffRepository staffRepository, StaffAssignmentRepository staffAssignmentRepository, ScreeningRepository screeningRepository) {
        this.staffRepository = staffRepository;
        this.staffAssignmentRepository = staffAssignmentRepository;
        this.screeningRepository = screeningRepository;
    }

    public void saveStaff(Staff staff){
        if (staff.getName() == null){
            throw new IllegalArgumentException("Staff must have a name!");
        }
        staffRepository.save(staff);
    }

    public Staff getStaffById(String id){
        return staffRepository.findById(id);
    }

    public List<Staff> findAllStaff(){
        return staffRepository.findAll();
    }

    public void deleteStaff(String id){
        if(staffRepository.findById(id) == null){
            throw new IllegalArgumentException("Can't delete Staff that doesnt exist");
        }
        staffRepository.delete(id);
    }

    public void saveStaffAssignment(StaffAssignment staffAssignment){
        if (staffRepository.findById(staffAssignment.getStaffId()) == null){
            throw new IllegalArgumentException("Staff doesn't exist");
        }
        if(screeningRepository.findById(staffAssignment.getScreeningId()) == null){
            throw new IllegalArgumentException("Screening doesn't exist!!!");
        }
        staffAssignmentRepository.save(staffAssignment);
    }

    public void deleteStaffAssignment(String staffAssignmentId){
        if (staffAssignmentRepository.findById(staffAssignmentId) == null){
            throw new IllegalArgumentException("Staff Assignment doesn't exist!");
        }
        staffAssignmentRepository.delete(staffAssignmentId);
    }

    public StaffAssignment findStaffAssignmentById(String id){
        return staffAssignmentRepository.findById(id);
    }

    public List<StaffAssignment> findAllStaffAssignments(){
        return staffAssignmentRepository.findAll();
    }

    public List<Staff> findStaffForScreening(String screeningId){
        Screening screening = screeningRepository.findById(screeningId);
        List<Staff> assignedStaff = new ArrayList<>();
        for (StaffAssignment assignment: screening.getAssignments()){
            Staff staff = staffRepository.findById(assignment.getStaffId());
            if (staff != null) {
                assignedStaff.add(staff);
            }
        }
        return assignedStaff;
    }
}