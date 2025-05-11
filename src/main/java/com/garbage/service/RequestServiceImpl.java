package com.garbage.service;

import com.garbage.model.Request;
import com.garbage.model.WorkAssign;
import com.garbage.repository.RequestRepository;
import com.garbage.repository.WorkAssignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private WorkAssignRepository workAssignRepository;

    /**
     * Implementation of the method to fetch requests that are not assigned
     * or have status 'pending'.
     *
     * @return List of Request objects
     */
    @Override
    public List<Request> getRequests() {
        return requestRepository.findRequestRequestsWithoutAssignmentsOrPending();
    }

    /**
     * Assigns a police officer to a specific request.
     *
     * @param requestId the ID of the request to assign
     * @param policeId  the ID of the police officer to assign
     */
    @Override
    public void assignPoliceToRequest(int requestId, int policeId) {
        WorkAssign workAssign = new WorkAssign();
        workAssign.setRequestId(requestId); // Updated method name
        workAssign.setPoliceId(policeId);  // Updated to policeId
        workAssign.setStatus("pending");
        workAssignRepository.save(workAssign);
    }
}
