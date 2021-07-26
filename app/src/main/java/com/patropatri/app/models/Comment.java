package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Comment {

    @SerializedName("CommentId")
    @Expose
    private String commentId;
    @SerializedName("ProductId")
    @Expose
    private String productId;
    @SerializedName("Comments")
    @Expose
    private String comments;
    @SerializedName("AuthId")
    @Expose
    private String authId;
    @SerializedName("FullName")
    @Expose
    private String fullName;
    @SerializedName("ImageName")
    @Expose
    private String imageName;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;
    @SerializedName("CreatedAt")
    @Expose
    private String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

    public Comment(String comments, String fullName) {
        this.comments = comments;
        this.fullName = fullName;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
