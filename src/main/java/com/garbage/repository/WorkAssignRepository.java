package com.garbage.repository;

import com.garbage.model.WorkAssign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface WorkAssignRepository extends JpaRepository<WorkAssign, Integer> {

    // Find a single WorkAssign by requestId and status
    Optional<WorkAssign> findByRequestIdAndStatus(int requestId, String status);
    List<WorkAssign> findByRequestIdInAndStatus(List<Integer> requestIds, String status);
    List<WorkAssign> findByRequestId(Integer requestId);
    List<WorkAssign> findByRequestIdIn(List<Integer> requestIds);
    // Find all WorkAssign records for a given policeId
    List<WorkAssign> findByPoliceId(Integer policeId);

    // Find assigned work details for a specific police officer with pending status
    @Query("SELECT wa.requestId, r.requestAddress, r.longitude, r.latitude FROM WorkAssign wa " +
           "JOIN Request r ON wa.requestId = r.requestId " +
           "WHERE wa.policeId = :policeId AND wa.status = 'Pending'")
    List<Object[]> findAssignedWorkByPoliceId(@Param("policeId") Integer policeId);

    // Find completed work details as a map
    @Query("SELECT new map(wa.assignId as assignId, wa.requestId as requestId, wa.policeId as policeId, wa.investigatedImage as investigatedImage, wa.status as status) " +
           "FROM WorkAssign wa " +
           "WHERE wa.status = 'Completed'")
    List<Map<String, Object>> findCompletedWorkDetails();

    // Count records by status (completed or pending)
    int countByStatus(String status);

    // Count records with null requestId (not assigned)
    int countByRequestIdIsNull();

    // Count records for a specific police officer with a given status
    long countByPoliceIdAndStatus(Integer policeId, String status);
}
