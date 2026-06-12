package com.clothingstore.patterns.sessionsingleton;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Session {

    private String username;
    private LocalDateTime cratedAt;
    private String role;
    private int id;
    private static Session instance;

    private Session(String username, String role, int id) {
        this.role = role;
        this.username = username;
        this.id = id;
        this.cratedAt = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Session() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCratedAt() {
        return cratedAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }
}