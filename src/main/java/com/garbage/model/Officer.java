package com.garbage.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Officer")
public class Officer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer officer_id;

    private String name;
    private String email;
    private String dob;
    private String password;
    private String gender;

    // Getters and Setters
    public Integer getofficer_id() {
        return officer_id;
    }

    public void setofficer_id(Integer officer_id) {
        this.officer_id = officer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
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
}
