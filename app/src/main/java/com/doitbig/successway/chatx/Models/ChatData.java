package com.doitbig.successway.chatx.Models;

public class ChatData {

    String timestamp;
    String val;
    String user;

    public ChatData(String timestamp, String val, String user)
    {
        this.timestamp = timestamp;
        this.user = user;
        this.val = val;
    }

    public String getVal()
    {
        return this.val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
