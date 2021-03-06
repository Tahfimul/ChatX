package com.doitbig.successway.chatx.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.doitbig.successway.chatx.Interfaces.ChatWindow;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.Models.FriendData;
import com.doitbig.successway.chatx.Repos.ChatWindowRepo;

import java.util.List;

public class ChatWindowViewModel extends ViewModel implements ChatWindow {

    ChatWindowRepo mRepo = new ChatWindowRepo();

    @Override
    public LiveData<ChatData> getMessages(String mFriendUser) {

        return mRepo.getMessages(mFriendUser);
    }

    @Override
    public void sendMessage(String mMessage) {
        if(!mMessage.isEmpty())
            mRepo.sendMessage(mMessage);
    }

    public FriendData getFriendUser()
    {
        return mRepo.getFriendUser();
    }
}
