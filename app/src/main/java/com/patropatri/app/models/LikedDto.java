package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class LikedDto {

    @SerializedName("id")
    @Expose
    private Long id;

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
