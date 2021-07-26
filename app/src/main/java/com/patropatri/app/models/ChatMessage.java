package com.patropatri.app.models;

import java.sql.Timestamp;

public class ChatMessage {

    private boolean isUser;
    private String eId;
    private String chatText;
    private String attachmentUrl;
    //private String imageUrl;
    private String createdAt = String.valueOf(new Timestamp(System.currentTimeMillis()));

    public ChatMessage() {
    }

    public ChatMessage(boolean isUser, String eId, String chatText, String attachmentUrl) {
        this.isUser = isUser;
        this.eId = eId;
        this.chatText = chatText;
        this.attachmentUrl = attachmentUrl;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
