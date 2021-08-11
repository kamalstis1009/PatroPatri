package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Inbox {

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

    @SerializedName("isEnabled")
    @Expose
    private Boolean isEnabled;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

}
