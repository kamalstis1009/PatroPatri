package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SendLetter {

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
}
