package com.kingdavies.africa.blueschatapp;


import org.springframework.messaging.MessageHeaders;

public class Message implements org.springframework.messaging.Message {
    private String user;
    private String message;

//    public Message() {
//        this.user ="Anonymous";
//        this.message = "No message";
//    }

    public Message(String user, String message) {
        this.user = user;
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public MessageHeaders getHeaders() {
        return null;
    }
}
