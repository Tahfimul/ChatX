package com.doitbig.successway.chatx.DB;

import android.support.annotation.NonNull;
import com.doitbig.successway.chatx.Interfaces.ChatRepo;
import com.doitbig.successway.chatx.LiveData.ChatDataLiveData;
import com.doitbig.successway.chatx.Models.ChatData;
import com.google.firebase.database.*;

public class DB implements ChatRepo {
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();


    @Override
    public ChatDataLiveData getMessage(String location) {
        return null;
    }

    @Override
    public void postMessage(ChatData mData) {
        mRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(mData.getUser())&&dataSnapshot.child(mData.getUser()).getValue().equals("Active")) {
                    long timeStamp = System.currentTimeMillis()/1000L;
                    mRef.child("Messages").child(String.valueOf(timeStamp)).child(mData.getUser()).setValue(mData.getVal());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
