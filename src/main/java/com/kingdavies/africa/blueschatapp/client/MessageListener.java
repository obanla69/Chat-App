package com.kingdavies.africa.blueschatapp.client;

import com.kingdavies.africa.blueschatapp.Message;

import java.util.ArrayList;

public interface MessageListener {
    void onMessageReceive(Message message);
    void onActiveUsersUpdated(ArrayList<String>users);

}
