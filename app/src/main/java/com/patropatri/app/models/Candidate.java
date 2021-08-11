package com.patropatri.app.models;

import java.util.Date;

import lombok.Data;

@Data
public class Candidate {

    //String.valueOf(new Timestamp(System.currentTimeMillis()));

    private Long id;
    private String fullName;
    private String gender;
    private String profession;
    private String district;
    private String summary;
    private Integer commentAmount;
    private Integer likedAmount;
    private String imageName;
    private Date createdAt;

}
