package com.doitbig.successway.chatx.Models;

import java.util.List;

public class ChatData {

    String presenceMessage;
    List<MessagesData> messagesData;

    public ChatData(String presenceMessage, List<MessagesData> messagesData)
    {
        this.presenceMessage = presenceMessage;
        this.messagesData = messagesData;

    }

    public void setPresenceMessage(String presenceMessage) {
        this.presenceMessage = presenceMessage;
    }

    public String getPresenceMessage() {
        return presenceMessage;
    }

    public void setMessagesData(List<MessagesData> messagesData) {
        this.messagesData = messagesData;
    }

    public List<MessagesData> getMessagesData() {
        return messagesData;
    }
}
