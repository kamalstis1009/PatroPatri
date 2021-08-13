package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDto {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("imageName")
    @Expose
    private String imageName;

    @SerializedName("userId")
    @Expose
    private Long userId;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

}
