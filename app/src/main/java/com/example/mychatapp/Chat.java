package com.example.mychatapp;

public class Chat  {
    private String username;
    private String lastMessage;
    private String date;
    private int avatar;

    public Chat(String username, String lastMessage, String date,int avatar) {
        this.username = username;
        this.lastMessage = lastMessage;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
