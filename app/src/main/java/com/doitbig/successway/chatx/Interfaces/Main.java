package com.doitbig.successway.chatx.Interfaces;

import com.doitbig.successway.chatx.LiveData.ChatDataLiveData;
import com.doitbig.successway.chatx.LiveData.FriendsDataLiveData;
import com.doitbig.successway.chatx.Models.ChatData;

public interface Main {

    public FriendsDataLiveData getFriends(String location);

    public void postMessage(ChatData mData);
}
