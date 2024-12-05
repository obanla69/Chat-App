package com.kingdavies.africa.blueschatapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final WebSocketSessionManager sessionManager;


    @Autowired
    public WebsocketController(SimpMessagingTemplate messagingTemplate, WebSocketSessionManager sessionManager) {
        this.messagingTemplate = messagingTemplate;
        this.sessionManager = sessionManager;
    }

    @MessageMapping("/message")
    public void handlerMessage( Message message){
        System.out.print("Received message from user:" + message.getUser() + ": "+ message.getMessage());
        messagingTemplate.convertAndSend("/topic/messages",message);
        System.out.println("Sent message to /topic/ message: " + message.getUser() +": " + message.getMessage());
    }
    @MessageMapping("/connect")
    public void connectUser(String username){
        sessionManager.addUsername(username);
        sessionManager.broadcastActiveUsername();
        System.out.print(username + "connected");
    }
    @MessageMapping("/disconnect")
    public void disconnectUser(String username){
        sessionManager.removeUsername(username);
        sessionManager.broadcastActiveUsername();
        System.out.println(username + " disconnected ");
    }
    @MessageMapping("/request-users")
    public void requestUser(){
        sessionManager.broadcastActiveUsername();
        System.out.println("Requesting Users");
    }
}
