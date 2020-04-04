package com.example.mychatapp;

import androidx.annotation.NonNull;

public class Message {
    private String Sender;
    private String Receiver;
    private String Message;
    private String receiverAvatar;

    public Message(String sender, String receiver, String message,String receiverAvatar) {
        this.Sender = sender;
        this.Receiver = receiver;
        this.Message = message;
        this.receiverAvatar = receiverAvatar;
    }
    public Message(){

    }

    public String getReceiverAvatar() {
        return receiverAvatar;
    }

    public void setReceiverAvatar(String receiverAvatar) {
        this.receiverAvatar = receiverAvatar;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        this.Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        this.Receiver = receiver;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return this.Message+ " " + this.Sender + " " + this.Receiver;
    }
}
