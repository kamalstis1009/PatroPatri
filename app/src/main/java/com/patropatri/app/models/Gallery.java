package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Gallery {

    //String.valueOf(new Timestamp(System.currentTimeMillis()));

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("imageName")
    @Expose
    private String imageName;

    @SerializedName("userId")
    @Expose
    private long userId;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

}
