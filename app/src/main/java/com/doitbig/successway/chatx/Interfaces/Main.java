package com.doitbig.successway.chatx.Interfaces;

import com.doitbig.successway.chatx.LiveData.FriendsDataLiveData;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.Models.MessagesData;

public interface Main {

    public FriendsDataLiveData getFriends(String location);

    public void postMessage(MessagesData mData);
}
