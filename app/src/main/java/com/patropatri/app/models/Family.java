package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Family {

    //String.valueOf(new Timestamp(System.currentTimeMillis()));

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("relation")
    @Expose
    private String relation;

    @SerializedName("occupation")
    @Expose
    private String occupation;

    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("userId")
    @Expose
    private Long userId;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;
}
