package com.patropatri.app.models;

import java.util.Date;

import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private String message;
    private String fullName;
    private String imageName;
    private Long userId;
    private Date createdAt;

}
