package com.doitbig.successway.chatx.Interfaces;

import com.doitbig.successway.chatx.LiveData.ChatDataLiveData;
import com.doitbig.successway.chatx.Models.ChatData;

public interface ChatRepo {

    public ChatDataLiveData getMessage(String location);

    public void postMessage(ChatData mData);
}
