package com.garbage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "police")
public class Police {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer police_id;

    private String name;
    private String email;
    private String dob;
    private String password;
    private String gender;

    @Column(name = "worker_status")
    private String status;

    // Getters and Setters
    public Integer getPolice_id() {
        return police_id;
    }

    public void setPolice_id(Integer police_id) {
        this.police_id = police_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
