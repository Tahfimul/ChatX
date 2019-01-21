package com.doitbig.successway.chatx.Models;

public class FriendData {
    String mUID;
    String mUser;
    String mStatus;

    public FriendData(String mUID, String mUser, String mStatus)
    {
        this.mUID = mUID;
        this.mUser = mUser;
        this.mStatus = mStatus;
    }

    public void setmUID(String mUID) {
        this.mUID = mUID;
    }

    public String getmUID() {
        return mUID;
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
