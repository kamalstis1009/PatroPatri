package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class PostalCode {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("districtId")
    @Expose
    private Long districtId;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

}
