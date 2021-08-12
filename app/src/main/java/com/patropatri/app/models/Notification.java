package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class Notification {

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

    @SerializedName("senderName")
    @Expose
    private String senderName;

    @SerializedName("senderImage")
    @Expose
    private String senderImage;

    @SerializedName("isEnabled")
    @Expose
    private Boolean isEnabled;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

}
