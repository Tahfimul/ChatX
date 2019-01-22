package com.doitbig.successway.chatx.Repos;

import android.arch.lifecycle.LiveData;
import com.doitbig.successway.chatx.Interfaces.StartNewConversation;
import com.doitbig.successway.chatx.LiveData.AllUsersLiveData;
import com.doitbig.successway.chatx.Models.UserData;

import java.util.List;

public class StartNewConversationRepo implements StartNewConversation {

    @Override
    public void SendNewMessage(String mFriendUser, String mMessage) {

    }

    @Override
    public LiveData<List<UserData>> getAllUsers() {
        return new AllUsersLiveData();
    }

}
