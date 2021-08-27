package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class User {

    //String.valueOf(new Timestamp(System.currentTimeMillis()));

    @SerializedName("id")
    @Expose
    private Long id;

    //---------------------| Personal
    @SerializedName("fullName")
    @Expose
    private String fullName;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("birthDate")
    @Expose
    private String birthDate;

    @SerializedName("birthPlace")
    @Expose
    private String birthPlace;

    @SerializedName("height")
    @Expose
    private String height;

    @SerializedName("weight")
    @Expose
    private String weight;

    @SerializedName("maritalStatus")
    @Expose
    private String maritalStatus;

    @SerializedName("religion")
    @Expose
    private String religion;

    @SerializedName("complex")
    @Expose
    private String complex;

    @SerializedName("district")
    @Expose
    private String district;

    //---------------------| Profession
    @SerializedName("profession")
    @Expose
    private String profession;

    @SerializedName("designation")
    @Expose
    private String designation;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("companyAddress")
    @Expose
    private String companyAddress;

    @SerializedName("companyWebsite")
    @Expose
    private String companyWebsite;

    //---------------------| Contact
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("presentAddress")
    @Expose
    private String presentAddress;

    @SerializedName("permanentAddress")
    @Expose
    private String permanentAddress;

    //---------------------| Other
    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("interest")
    @Expose
    private String interest;

    @SerializedName("otherActivity")
    @Expose
    private String otherActivity;

    @SerializedName("expectation")
    @Expose
    private String expectation;

    //---------------------| Image
    @SerializedName("imageName")
    @Expose
    private String imageName;

    @SerializedName("imageCoverName")
    @Expose
    private String imageCoverName;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    @SerializedName("isApproved")
    @Expose
    private Boolean isApproved;

    //---------------------| Count
    @SerializedName("commentAmount")
    @Expose
    private Integer commentAmount;

    @SerializedName("likedAmount")
    @Expose
    private Integer likedAmount;

    @SerializedName("viewerAmount")
    @Expose
    private Integer viewerAmount;

    //---------------------| Audit
    @SerializedName("createdAt")
    @Expose
    private Date createdAt; // String.valueOf(new Timestamp(System.currentTimeMillis()));

    @SerializedName("createdBy")
    @Expose
    private Long createdBy;

    @SerializedName("updatedAt")
    @Expose
    private Date updatedAt;

    @SerializedName("updatedBy")
    @Expose
    private Long updatedBy;

}
