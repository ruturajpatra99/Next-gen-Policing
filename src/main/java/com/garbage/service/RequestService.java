package com.garbage.service;

import com.garbage.model.Request;
import java.util.List;

public interface RequestService {
    /**
     * Retrieves a list of all requests.
     *
     * @return a list of Request objects
     */
    List<Request> getRequests();

    /**
     * Assigns a police officer to a specific request.
     *
     * @param requestId the ID of the request to be assigned
     * @param policeId  the ID of the police officer to assign
     */
    void assignPoliceToRequest(int requestId, int policeId);
}
