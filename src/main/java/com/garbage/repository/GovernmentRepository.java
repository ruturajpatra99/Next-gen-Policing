package com.garbage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garbage.model.Government;

public interface GovernmentRepository extends JpaRepository<Government, Integer> {
    Government findByIdAndPassword(Integer id, String password);
}
