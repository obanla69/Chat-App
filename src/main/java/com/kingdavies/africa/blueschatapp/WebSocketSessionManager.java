package com.kingdavies.africa.blueschatapp;


import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class WebSocketSessionManager {
    private final ArrayList<String> activeUsername = new ArrayList<>();
    private final SimpMessagingTemplate messagingTemplate;


    public WebSocketSessionManager(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }
    public void addUsername(String username){
        activeUsername.add(username);
    }
    public void removeUsername(String username){
        activeUsername.remove(username);
    }
    public void broadcastActiveUsername(){
        messagingTemplate.convertAndSend("/topic/users",activeUsername);
        System.out.println("Broadcasting active users to /topic/users " + activeUsername);
    }
}
