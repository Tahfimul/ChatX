package com.doitbig.successway.chatx.Models;

public class UserData {

    boolean mHighlighted;
    String mUID;
    String mUsername;
    boolean mUserStatus;

    public UserData(String mUID, String mUsername, boolean mUserStatus, long mTimeStamp)
    {
        this.mUID = mUID;
        this.mUsername = mUsername;
        this.mUserStatus = mUserStatus;
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

    public void setmUserStatus(boolean mUserStatus) {
        this.mUserStatus = mUserStatus;
    }

    public boolean isUserActive() {
        return mUserStatus;
    }

    public void setmHighlighted(boolean mHighlighted) {
        this.mHighlighted = mHighlighted;
    }

    public boolean ismHighlighted() {
        return mHighlighted;
    }
}
