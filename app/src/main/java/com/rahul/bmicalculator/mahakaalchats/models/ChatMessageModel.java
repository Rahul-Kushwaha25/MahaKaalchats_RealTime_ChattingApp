package com.rahul.bmicalculator.mahakaalchats.models;

import com.google.firebase.Timestamp;

public class ChatMessageModel {

    private String message;
    private  String sender_userid;
    private Timestamp timestamp;

    public ChatMessageModel() {
    }

    public ChatMessageModel(String message, String sender_userid, Timestamp timestamp) {
        this.message = message;
        this.sender_userid = sender_userid;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender_userid() {
        return sender_userid;
    }

    public void setSender_userid(String sender_userid) {
        this.sender_userid = sender_userid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
