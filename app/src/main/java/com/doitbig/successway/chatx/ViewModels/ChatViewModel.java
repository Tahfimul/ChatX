package com.doitbig.successway.chatx.ViewModels;

import android.arch.lifecycle.ViewModel;
import com.doitbig.successway.chatx.LiveData.ChatDataLiveData;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.Repos.ChatRepo;

public class ChatViewModel extends ViewModel implements com.doitbig.successway.chatx.Interfaces.ChatRepo {


    ChatRepo mRepo = new ChatRepo();
    @Override
    public ChatDataLiveData getMessage(String location) {
        return mRepo.getMessage(location);
    }

    @Override
    public void postMessage(ChatData mData) {
        mRepo.postMessage(mData);
    }
}
