package com.kingdavies.africa.blueschatapp.client;

import com.kingdavies.africa.blueschatapp.Message;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {
    private String username;
    private MessageListener messageListener;

    public MyStompSessionHandler(MessageListener messageListener, String username) {
        this.username = username;
        this.messageListener = messageListener;

    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("Client Connected");
        session.subscribe("/topic/messages", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return Message.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try{
                    if (payload instanceof Message) {
                       Message message = (Message) payload;
                        messageListener.onMessageReceive(message);
                        System.out.println("Received message:" + message.getUser() + ": " + message.getMessage());
                    }else{
                        System.out.print("Received unexpected payload type:" + payload.getClass());
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        System.out.println("Client Subscribe to/topic/messages");

        session.subscribe("/topic/users", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return new ArrayList<String>().getClass();
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                try{
                    if (payload instanceof ArrayList){
                        ArrayList<String> activeUsers = (ArrayList<String>) payload;
                        messageListener.onActiveUsersUpdated(activeUsers);
                        System.out.println("Received active users: " + activeUsers);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });
        System.out.println("Subscribed to /topic/users");

        session.send("/app/connect",username);

        session.send("/app/request-users", "");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {

    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        exception.printStackTrace();

    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return null;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {

    }
}
