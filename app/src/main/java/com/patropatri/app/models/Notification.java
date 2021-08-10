package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
