package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class Viewed {

    @SerializedName("id")
    @Expose
    private Long id;

    //@Column(name = "amount")
    //private int amount;

    @SerializedName("userId")
    @Expose
    private Long userId;

    @SerializedName("createdAt")
    @Expose
    private Date createdAt;

    @SerializedName("createdBy")
    @Expose
    private Long createdBy;

}
