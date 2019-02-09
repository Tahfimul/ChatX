package com.doitbig.successway.chatx.Models;

public class FriendMainAdapterData {
    String mUID;
    String mUser;
    String mLatestMessage;
    boolean mStatus;
    boolean mIsChecked;

    public FriendMainAdapterData(String mUID, String mUser, String mLatestMessage, boolean mStatus) {
        this.mUID = mUID;
        this.mUser = mUser;
        this.mLatestMessage = mLatestMessage;
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

    public void setmLatestMessage(String mLatestMessage) {
        this.mLatestMessage = mLatestMessage;
    }

    public String getmLatestMessage() {
        return mLatestMessage;
    }

    public void setmStatus(boolean mStatus) {
        this.mStatus = mStatus;
    }

    public boolean isActive() {
        return mStatus;
    }

    public void setmIsChecked(boolean mIsChecked) {
        this.mIsChecked = mIsChecked;
    }

    public boolean ismIsChecked() {
        return mIsChecked;
    }
}
