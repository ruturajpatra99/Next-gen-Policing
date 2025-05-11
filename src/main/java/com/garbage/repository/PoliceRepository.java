package com.garbage.repository;

import com.garbage.model.Police;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface PoliceRepository extends JpaRepository<Police, Integer> {
    Optional<Police> findByemailAndPassword(String email, String password);

    // List<Worker> findByStatus(String worker_status);
}
