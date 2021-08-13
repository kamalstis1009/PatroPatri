package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class Comment {

    //String.valueOf(new Timestamp(System.currentTimeMillis()));

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("userId")
    @Expose
    private Long userId;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    @SerializedName("createdBy")
    @Expose
    private Long createdBy;

}
