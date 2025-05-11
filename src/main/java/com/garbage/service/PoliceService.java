package com.garbage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garbage.model.Police;
import com.garbage.repository.WorkAssignRepository;
import com.garbage.repository.PoliceRepository;

@Service
public class PoliceService {
    @Autowired
    private PoliceRepository policeRepository; // Updated variable name for clarity
    @Autowired
    private WorkAssignRepository workAssignRepository;

    // Get all police officers
    public List<Police> getAllPolice() {
        return policeRepository.findAll();
    }

    // Get the count of completed work
    public int getCompletedWorkCount() {
        return workAssignRepository.countByStatus("completed");
    }

    // Get the count of pending work
    public int getPendingWorkCount() {
        return workAssignRepository.countByStatus("pending");
    }

    // Get the count of work not assigned to request ID
    public int getUnassignedWorkCount() {
        return workAssignRepository.countByRequestIdIsNull();
    }
}
