package com.doitbig.successway.chatx.Repos;

import android.arch.lifecycle.LiveData;
import com.doitbig.successway.chatx.Interfaces.ChatWindow;
import com.doitbig.successway.chatx.LiveData.ChatDataLiveData;
import com.doitbig.successway.chatx.Models.ChatData;

import java.util.List;

public class ChatWindowRepo implements ChatWindow {

    @Override
    public LiveData<List<ChatData>> getMessages(String mFriendUser) {
        return new ChatDataLiveData(mFriendUser);
    }

}
