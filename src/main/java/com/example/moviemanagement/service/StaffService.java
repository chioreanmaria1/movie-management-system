package com.example.moviemanagement.service;

import com.example.moviemanagement.model.*;
import com.example.moviemanagement.repository.BaseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {
    public final BaseRepository<Staff> supportStaffRepository;
    public final BaseRepository<Staff> technicalOperatorRepository;
    public final BaseRepository<StaffAssignment> staffAssignmentRepository;
    public final BaseRepository<Screening> screeningRepository;

    public StaffService(BaseRepository<Staff> supportStaffRepository, BaseRepository<Staff> technicalOperatorRepository, BaseRepository<StaffAssignment> staffAssignmentRepository, BaseRepository<Screening> screeningRepository) {
        this.supportStaffRepository = supportStaffRepository;
        this.technicalOperatorRepository = technicalOperatorRepository;
        this.staffAssignmentRepository = staffAssignmentRepository;
        this.screeningRepository = screeningRepository;
    }

    public void saveStaff(Staff staff){
        if (staff.getName() == null || staff.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Staff must have a name!");
        }
        if (staff instanceof SupportStaff){
            if (((SupportStaff) staff).getRole() == null){
                throw new IllegalArgumentException("Support Staff must have a role!");
            }
            if (technicalOperatorRepository.findById(staff.getId()) != null){
                throw new IllegalArgumentException("Id " + staff.getId() + " is already in use!");
            }
            supportStaffRepository.save(staff);
        }
        else if (staff instanceof TechnicalOperator){
            if (((TechnicalOperator) staff).getSpecialization() == null){
                throw new IllegalArgumentException("Technical Operator must have a specialization");
            }
            if (supportStaffRepository.findById(staff.getId()) != null){
                throw new IllegalArgumentException("Id " + staff.getId() + " is already in use!");
            }
            technicalOperatorRepository.save(staff);
        }
        else{
            throw new IllegalArgumentException("Staff must be either SupportStaff or TechnicalOperator!");
        }
    }

    public Staff findStaffById(String staffId){
        Staff staff = supportStaffRepository.findById(staffId);
        if (staff != null){
            return staff;
        }
        staff = technicalOperatorRepository.findById(staffId);
        if (staff == null){
            throw new IllegalArgumentException("Staff member with id " + staffId + " not found!");
        }
        return staff;
    }

    public List<Staff> findAllSupportStaff(){
        return supportStaffRepository.findAll();
    }
    public List<Staff> findAllTechnicalOperator(){
        return technicalOperatorRepository.findAll();
    }
    public List<Staff> findAllTechnicalOperators(){
        return findAllTechnicalOperator();
    }

    public void deleteStaff(String staffId){
        Staff staff = findStaffById(staffId);
        List<String> assignmentIds = new ArrayList<>();
        for(StaffAssignment assignment : staffAssignmentRepository.findAll()){
            if(assignment.getStaffId().equals(staffId)){
                assignmentIds.add(assignment.getId());
            }
        }
        for(String assignmentId : assignmentIds){
            staffAssignmentRepository.delete(assignmentId);
        }
        if (staff instanceof SupportStaff){
            supportStaffRepository.delete(staffId);
        }
        else if (staff instanceof TechnicalOperator)
        {
            technicalOperatorRepository.delete(staffId);
        }
    }

    public void saveStaffAssignment(StaffAssignment staffAssignment){
        Staff staff = findStaffById(staffAssignment.getStaffId());
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
        List<Staff> assignedStaff = new ArrayList<>();
        for (StaffAssignment assignment: staffAssignmentRepository.findAll()){
            if (!screeningId.equals(assignment.getScreeningId())) continue;
            Staff staff = findStaffById(assignment.getStaffId());
            assignedStaff.add(staff);
        }
        return assignedStaff;
    }
}