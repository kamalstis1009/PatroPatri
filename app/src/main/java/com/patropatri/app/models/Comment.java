package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Comment {

    //String.valueOf(new Timestamp(System.currentTimeMillis()));

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

}
