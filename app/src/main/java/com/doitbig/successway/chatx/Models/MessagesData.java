package com.doitbig.successway.chatx.Models;

public class MessagesData {

    int state;
    long timestamp;
    String val;
    String user;

    public MessagesData(int state, long timestamp, String val, String user)
    {
        this.state = state;
        this.timestamp = timestamp;
        this.user = user;
        this.val = val;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
