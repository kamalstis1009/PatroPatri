package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class Message {

    //String.valueOf(new Timestamp(System.currentTimeMillis()));

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("imageFiles")
    @Expose
    private String [] imageFiles;

    @SerializedName("senderId")
    @Expose
    private Long senderId;

    @SerializedName("receiverId")
    @Expose
    private Long receiverId;

    @SerializedName("isEnabled")
    @Expose
    private Boolean isEnabled;

    @SerializedName("inboxId")
    @Expose
    private Long inboxId;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;
}
