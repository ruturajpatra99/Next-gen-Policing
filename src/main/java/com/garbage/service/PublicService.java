package com.garbage.service;

import com.garbage.model.Public;
import com.garbage.model.Request;
import com.garbage.model.WorkAssign;
import com.garbage.repository.PublicRepository;
import com.garbage.repository.RequestRepository;
import com.garbage.repository.WorkAssignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicService {

    @Autowired
    private PublicRepository publicRepository;

    @Autowired
    private WorkAssignRepository workAssignRepository;

    @Autowired
    private RequestRepository requestRepository;

    public Optional<Public> getPublicById(Integer publicId) {
        return publicRepository.findByPublicId(publicId);
    }

   

    public List<WorkAssign> getWorkAssignStatusByPublicId(Integer publicId) {
        // Convert publicId to String if needed
        String publicIdString = publicId.toString();

        // Find public details using publicId
        Optional<Public> publicOptional = publicRepository.findByPublicId(publicId);

        if (publicOptional.isPresent()) {
            // Fetch requests based on publicId (now as String)
            List<Request> requests = requestRepository.findByPublicId(publicIdString);

            if (requests != null && !requests.isEmpty()) {
                // Extract the list of requestIds from the requests
                List<Integer> requestIds = requests.stream()
                                                   .map(Request::getRequestId)
                                                   .toList();

                // Fetch work assignments for the requestIds
                return workAssignRepository.findByRequestIdIn(requestIds);
            }
        }

        return null;
    }
}