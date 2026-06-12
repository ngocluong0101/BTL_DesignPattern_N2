package com.clothingstore.model;


import java.util.Date;

public class Notification {
    private int customerId;
    private int eventId;
    private String message;
    private Date sentAt;
    private String status;

    public Notification(int customerId, int eventId, String message, Date sentAt, String status) {
        this.customerId = customerId;
        this.eventId = eventId;
        this.message = message;
        this.sentAt = sentAt;
        this.status = status;
    }

    // Getters
    public int getCustomerId() {
        return customerId;
    }

    public int getEventId() {
        return eventId;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public Date getSentAt() {
        return sentAt;
    }
}