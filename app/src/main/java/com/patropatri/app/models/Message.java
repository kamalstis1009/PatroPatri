package com.patropatri.app.models;

import java.sql.Timestamp;

public class Message {

    private int id;
    private String text;
    private String name;
    private String imageName;
    private String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

    public Message(String text, String name) {
        this.text = text;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
