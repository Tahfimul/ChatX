package com.doitbig.successway.chatx.ViewModels;

import android.arch.lifecycle.ViewModel;
import com.doitbig.successway.chatx.Interfaces.Main;
import com.doitbig.successway.chatx.LiveData.FriendsDataLiveData;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.Repos.MainRepo;

public class MainViewModel extends ViewModel implements Main {


    MainRepo mRepo = new MainRepo();

    @Override
    public FriendsDataLiveData getFriends(String location) {
        return mRepo.getFriends(location);
    }

    @Override
    public void postMessage(ChatData mData) {
        mRepo.postMessage(mData);
    }
}
