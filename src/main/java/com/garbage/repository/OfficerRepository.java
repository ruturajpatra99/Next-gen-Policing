package com.garbage.repository;

import com.garbage.model.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface OfficerRepository extends JpaRepository<Officer, Long> {
    Optional<Officer> findByemailAndPassword(String email, String password);
}