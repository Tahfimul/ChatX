package com.doitbig.successway.chatx.Interfaces;

import android.arch.lifecycle.LiveData;
import com.doitbig.successway.chatx.Models.ChatData;

import java.util.List;

public interface ChatWindow {
    public LiveData<List<ChatData>> getMessages(String mFriendUser);

    public void sendMessage(String mMessage);
}
