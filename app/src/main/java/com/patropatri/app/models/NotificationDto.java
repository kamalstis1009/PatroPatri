package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class NotificationDto {

    //String.valueOf(new Timestamp(System.currentTimeMillis()));

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("senderId")
    @Expose
    private Long senderId;

    @SerializedName("receiverId")
    @Expose
    private Long receiverId;

    @SerializedName("isEnabled")
    @Expose
    private Boolean isEnabled;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

}
