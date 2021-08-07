package com.patropatri.app.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Data;

@Data
public class User {

    @SerializedName("Id")
    @Expose
    private Long id;

    //---------------------| Personal
    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("birth_date")
    @Expose
    private Date birthDate;

    @SerializedName("birth_place")
    @Expose
    private String birthPlace;

    @SerializedName("height")
    @Expose
    private String height;

    @SerializedName("weight")
    @Expose
    private String weight;

    @SerializedName("marital_status")
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

    @SerializedName("company_address")
    @Expose
    private String companyAddress;

    @SerializedName("company_website")
    @Expose
    private String companyWebsite;

    //---------------------| Contact
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("present_address")
    @Expose
    private String presentAddress;

    @SerializedName("permanent_address")
    @Expose
    private String permanentAddress;

    //---------------------| Other
    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("interest")
    @Expose
    private String interest;

    @SerializedName("other_activity")
    @Expose
    private String otherActivity;

    @SerializedName("expectation")
    @Expose
    private String expectation;

    //---------------------| Image
    @SerializedName("image_name")
    @Expose
    private String imageName;

    @SerializedName("image_cover_name")
    @Expose
    private String imageCoverName;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("is_active")
    @Expose
    private Boolean isActive;

    //---------------------| Count
    @SerializedName("comment_amount")
    @Expose
    private Integer commentAmount;

    @SerializedName("liked_amount")
    @Expose
    private Integer likedAmount;

    //---------------------| Audit
    @SerializedName("created_at")
    @Expose
    private Date createdAt; // String.valueOf(new Timestamp(System.currentTimeMillis()));

    @SerializedName("created_by")
    @Expose
    private Long createdBy;

    @SerializedName("updated_at")
    @Expose
    private Date updatedAt;

    @SerializedName("updated_by")
    @Expose
    private Long updatedBy;

}
