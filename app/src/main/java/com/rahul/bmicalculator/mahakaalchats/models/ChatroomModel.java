package com.rahul.bmicalculator.mahakaalchats.models;

import com.google.firebase.Timestamp;

import java.util.List;

public class ChatroomModel {

    private String chatroomid;
    private List<String> userids;
    private Timestamp lastmsg_time;
    private String  lastsender_userid;
    private  String last_message;

    public ChatroomModel() {
    }

    public ChatroomModel(String chatroomid, List<String> userids, Timestamp lastmsg_time, String lastsender_userid) {
        this.chatroomid = chatroomid;
        this.userids = userids;
        this.lastmsg_time = lastmsg_time;
        this.lastsender_userid = lastsender_userid;

    }

    public String getChatroomid() {
        return chatroomid;
    }

    public void setChatroomid(String chatroomid) {
        this.chatroomid = chatroomid;
    }

    public List<String> getUserids() {
        return userids;
    }

    public void setUserids(List<String> userids) {
        this.userids = userids;
    }

    public Timestamp getLastmsg_time() {
        return lastmsg_time;
    }

    public void setLastmsg_time(Timestamp lastmsg_time) {
        this.lastmsg_time = lastmsg_time;
    }

    public String getLastsender_userid() {
        return lastsender_userid;
    }

    public void setLastsender_userid(String lastsender_userid) {
        this.lastsender_userid = lastsender_userid;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }
}
