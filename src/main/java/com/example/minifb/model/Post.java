package com.example.minifb.model;

import java.sql.Timestamp;

public class Post {
    private int id;
    private int userId;
    private String caption;
    private String imageUrl;
    private Timestamp createdAt;

    public Post() {}

    public Post(int userId, String caption, String imageUrl) {
        this.userId = userId; this.caption = caption; this.imageUrl = imageUrl;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
