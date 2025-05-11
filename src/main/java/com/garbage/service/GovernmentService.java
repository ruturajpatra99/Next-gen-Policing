package com.garbage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garbage.model.Government;
import com.garbage.repository.GovernmentRepository;

@Service
public class GovernmentService {

    @Autowired
    private GovernmentRepository governmentRepository;

    // Method to validate government credentials
    public Government validateGovernment(Integer id, String password) {
        return governmentRepository.findByIdAndPassword(id, password);
    }
}