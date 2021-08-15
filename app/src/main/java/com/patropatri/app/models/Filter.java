package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Filter {

    //String.valueOf(new Timestamp(System.currentTimeMillis()));

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;

    @SerializedName("religion")
    @Expose
    private String religion;

    @SerializedName("profession")
    @Expose
    private String profession;

    @SerializedName("fromYear")
    @Expose
    private Integer fromYear;

    @SerializedName("toYear")
    @Expose
    private Integer toYear;

    @SerializedName("district")
    @Expose
    private String district;
}
