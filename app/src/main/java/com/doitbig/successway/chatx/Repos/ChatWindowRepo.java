package com.doitbig.successway.chatx.Repos;

import android.arch.lifecycle.LiveData;
import com.doitbig.successway.chatx.DB.Firebase;
import com.doitbig.successway.chatx.Interfaces.ChatWindow;
import com.doitbig.successway.chatx.LiveData.ChatDataLiveData;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.Models.FriendData;

public class ChatWindowRepo implements ChatWindow {

    Firebase mDB = new Firebase();

    static FriendData mFriendUser;

    @Override
    public LiveData<ChatData> getMessages(String mFriendUser) {
        return new ChatDataLiveData(mFriendUser);
    }

    @Override
    public void sendMessage(String mMessage) {
        mDB.sendMessage(mMessage, getFriendUser().getmUID());
    }

    public void setFriendUser(FriendData mFriendUser)
    {
        this.mFriendUser = mFriendUser;
    }

    public FriendData getFriendUser()
    {
        return this.mFriendUser;
    }

}
