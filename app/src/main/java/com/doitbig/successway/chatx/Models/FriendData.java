package com.doitbig.successway.chatx.Models;

public class FriendData {
    String mUser;
    String mStatus;

    public FriendData(String mUser, String mStatus)
    {
        this.mUser = mUser;
        this.mStatus = mStatus;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public String getmUser() {
        return mUser;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmStatus() {
        return mStatus;
    }
}
