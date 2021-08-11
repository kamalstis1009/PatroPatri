package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class InboxDto {

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

}
