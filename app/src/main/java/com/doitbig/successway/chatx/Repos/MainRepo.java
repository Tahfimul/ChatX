package com.doitbig.successway.chatx.Repos;

import com.doitbig.successway.chatx.DB.DB;
import com.doitbig.successway.chatx.Interfaces.Main;
import com.doitbig.successway.chatx.LiveData.FriendsDataLiveData;
import com.doitbig.successway.chatx.Models.ChatData;

public class MainRepo implements Main {

    DB mDB = new DB();

    @Override
    public FriendsDataLiveData getFriends(String location) {
        return new FriendsDataLiveData(location);
    }

    @Override
    public void postMessage(ChatData mData) {
        mDB.postMessage(mData);
    }

    public void setUserSignedOut()
    {
        mDB.setUserSignedOut();
    }
}
