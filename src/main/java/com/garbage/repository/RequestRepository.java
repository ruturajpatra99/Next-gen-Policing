package com.garbage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.garbage.model.Request;
import com.garbage.model.WorkAssign;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    // Updated query to use requestId instead of garbageId
    @Query("SELECT r FROM Request r WHERE r.requestId NOT IN " +
            "(SELECT wa.requestId FROM WorkAssign wa) " +
            "OR r.requestId IN (SELECT wa.requestId FROM WorkAssign wa WHERE wa.status = 'pending')")
    List<Request> findRequestRequests();

    // Custom query to get requests that are not assigned or have status 'pending'
    @Query("SELECT r FROM Request r WHERE r.requestId NOT IN (SELECT wa.requestId FROM WorkAssign wa) " +
            "OR EXISTS (SELECT wa FROM WorkAssign wa WHERE wa.requestId = r.requestId AND wa.status != 'pending' AND wa.status != 'completed')")
    List<Request> findRequestRequestsWithoutAssignmentsOrPending();

    long count();

    List<Request> findByPublicId(String publicId);
}
