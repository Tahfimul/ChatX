package com.doitbig.successway.chatx.Models;

public class UserData {

    boolean mHighlighted;
    String mUID;
    String mUsername;

    public UserData(String mUID, String mUsername)
    {
        this.mUID = mUID;
        this.mUsername = mUsername;
    }

    public void setmUID(String mUID) {
        this.mUID = mUID;
    }

    public String getmUID() {
        return mUID;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmHighlighted(boolean mHighlighted) {
        this.mHighlighted = mHighlighted;
    }

    public boolean ismHighlighted() {
        return mHighlighted;
    }
}
