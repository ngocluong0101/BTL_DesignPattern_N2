package com.clothingstore.model;

import java.util.Date;

public class Event {
    private int id_events;
    private String name;
    private Date startDate;
    private Date endDate;
    private String content;
    private int adminId;

    public Event(String name, Date startDate, Date endDate, String content, int adminId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.adminId = adminId;
    }

    public int getId() {
        return id_events;
    }

    public void setId(int id) {
        this.id_events = id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getAdminId() {
        return adminId;
    }
}