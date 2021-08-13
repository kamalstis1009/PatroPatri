package com.patropatri.app.models;

import java.util.Date;

import lombok.Data;

@Data
public class LikedDto {

    private Long id;
    private String fullName;
    private String imageName;
    private Long userId;
    private Date createdAt;

}
