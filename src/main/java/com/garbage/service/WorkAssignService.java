package com.garbage.service;

import com.garbage.model.WorkAssign;
import com.garbage.repository.WorkAssignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WorkAssignService {

    @Autowired
    private WorkAssignRepository workAssignRepository;

    /**
     * Retrieves work assignments for a specific police officer by their ID.
     *
     * @param policeId the ID of the police officer
     * @return List of WorkAssign objects assigned to the police officer
     */
    public List<WorkAssign> getWorkAssignmentsByPoliceId(Integer policeId) {
        return workAssignRepository.findByPoliceId(policeId); // Updated method to reflect police ID
    }

    /**
     * Updates the investigated image and marks the assignment as completed.
     *
     * @param requestId         the ID of the request
     * @param investigatedImage the URL/path of the investigated image
     */
    public void updateInvestigatedImage(Integer requestId, String investigatedImage) {
        WorkAssign workAssign = workAssignRepository.findByRequestIdAndStatus(requestId, "Pending")
                .orElseThrow(() -> new RuntimeException("Assignment not found"));
        workAssign.setInvestigatedImage(investigatedImage); // Updated field name
        workAssign.setStatus("Completed");
        workAssignRepository.save(workAssign);
    }

    /**
     * Retrieves details of completed work assignments.
     *
     * @return List of completed work details as a map
     */
    public List<Map<String, Object>> getCompletedWorkDetails() {
        return workAssignRepository.findCompletedWorkDetails();
    }


    public List<WorkAssign> getAllWorkAssignments() {
        return workAssignRepository.findAll();
    }
}
