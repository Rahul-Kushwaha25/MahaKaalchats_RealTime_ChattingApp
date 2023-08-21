package com.rahul.bmicalculator.mahakaalchats.models;

import com.google.firebase.Timestamp;

public class usernamemodel {


    private String phone;
    private String username;
    private Timestamp timestamp;
    private String userid;

    public usernamemodel() {
    }

    public usernamemodel(String phone, String username, Timestamp timestamp,String userid) {
        this.phone = phone;
        this.username = username;
        this.timestamp = timestamp;
        this.userid = userid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
