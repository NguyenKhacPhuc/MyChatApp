package com.example.mychatapp;

public class Contacts {
    private int avatarImage;
    private String phoneNumber;
    private String bio;
    private String userName;

    public Contacts(int avatarImage, String phoneNumber,String userName){

        this.avatarImage = avatarImage;
        this.phoneNumber = phoneNumber;

        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(int avatarImage) {
        this.avatarImage = avatarImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
