package com.doitbig.successway.chatx.Interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.doitbig.successway.chatx.Models.UserData;

import java.util.List;

public interface StartNewConversation {
    public void SendNewMessage(String mFriendUser, String mMessage);

    public LiveData<List<UserData>> getAllUsers();
}
