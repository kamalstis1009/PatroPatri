package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Education {

    //String.valueOf(new Timestamp(System.currentTimeMillis()));

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("degree")
    @Expose
    private String degree;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("passingYear")
    @Expose
    private Integer passingYear;

    @SerializedName("result")
    @Expose
    private String result;

    @SerializedName("institution")
    @Expose
    private String institution;

    @SerializedName("userId")
    @Expose
    private Long userId;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

}
