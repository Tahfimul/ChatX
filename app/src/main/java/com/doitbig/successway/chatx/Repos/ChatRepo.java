package com.doitbig.successway.chatx.Repos;

import com.doitbig.successway.chatx.DB.DB;
import com.doitbig.successway.chatx.LiveData.ChatDataLiveData;
import com.doitbig.successway.chatx.Models.ChatData;
import com.google.firebase.database.*;

public class ChatRepo implements com.doitbig.successway.chatx.Interfaces.ChatRepo {

    DB mDB = new DB();
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    @Override
    public ChatDataLiveData getMessage(String location) {

        mRef = mRef.child(location);

        return new ChatDataLiveData(mRef);
    }

    @Override
    public void postMessage(ChatData mData) {
        mDB.postMessage(mData);
    }
}
