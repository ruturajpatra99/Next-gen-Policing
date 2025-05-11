package com.garbage.model;

import jakarta.persistence.*;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId; // Updated field name

    private String publicId;
    private String publicName;
    private String requestAddress; // Updated field name
    private String requestImage;   // Updated field name
    private String longitude;
    private String latitude;

    // Getters and Setters
    public int getRequestId() { // Updated getter method name
        return requestId;
    }

    public void setRequestId(int requestId) { // Updated setter method name
        this.requestId = requestId;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getRequestAddress() { // Updated getter method name
        return requestAddress;
    }

    public void setRequestAddress(String requestAddress) { // Updated setter method name
        this.requestAddress = requestAddress;
    }

    public String getRequestImage() { // Updated getter method name
        return requestImage;
    }

    public void setRequestImage(String requestImage) { // Updated setter method name
        this.requestImage = requestImage;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
