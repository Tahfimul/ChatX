package com.doitbig.successway.chatx.ViewModels;

import android.arch.lifecycle.ViewModel;
import com.doitbig.successway.chatx.Interfaces.Main;
import com.doitbig.successway.chatx.LiveData.FriendsDataLiveData;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.Models.FriendData;
import com.doitbig.successway.chatx.Models.MessagesData;
import com.doitbig.successway.chatx.Repos.MainRepo;

import java.util.List;

public class MainViewModel extends ViewModel implements Main {


    MainRepo mRepo = new MainRepo();

    @Override
    public FriendsDataLiveData getFriends(String location) {
        return mRepo.getFriends(location);
    }

    @Override
    public void postMessage(MessagesData mData) {
        mRepo.postMessage(mData);
    }

    public void setUserSignedOut()
    {
        mRepo.setUserSignedOut();
    }

    public void updateItems(List<String> mItems)
    {
        mRepo.updateItems(mItems);
    }
}
