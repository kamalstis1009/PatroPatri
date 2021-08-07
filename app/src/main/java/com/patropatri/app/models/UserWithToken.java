package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class UserWithToken {

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("auth")
    @Expose
    private Authentication auth;

    @SerializedName("user")
    @Expose
    private User user;

}
