package com.garbage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "work_assign")
public class WorkAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assign_id")
    private int assignId;

    @Column(name = "request_id") // Updated column name
    private int requestId;

    @Column(name = "police_id") // Updated column name
    private int policeId;

    @Column(name = "status")
    private String status;

    @Column(name = "investigated_image") // Updated column name
    private String investigatedImage; // Updated field name

    // Getters and Setters
    public int getAssignId() {
        return assignId;
    }

    public void setAssignId(int assignId) {
        this.assignId = assignId;
    }

    public int getRequestId() { // Updated getter method name
        return requestId;
    }

    public void setRequestId(int requestId) { // Updated setter method name
        this.requestId = requestId;
    }

    public int getPoliceId() { // Updated getter method name
        return policeId;
    }

    public void setPoliceId(int policeId) { // Updated setter method name
        this.policeId = policeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvestigatedImage() { // Updated getter method name
        return investigatedImage;
    }

    public void setInvestigatedImage(String investigatedImage) { // Updated setter method name
        this.investigatedImage = investigatedImage;
    }
}
