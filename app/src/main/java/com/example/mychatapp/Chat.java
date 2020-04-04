package com.example.mychatapp;

public class Chat  {
    private String username;
    private String lastMessage;
    private String avatar;

    public Chat(String username, String lastMessage,String avatar) {
        this.username = username;
        this.lastMessage = lastMessage;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
