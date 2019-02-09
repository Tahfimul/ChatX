package com.doitbig.successway.chatx.DB;

import android.support.annotation.NonNull;
import android.util.Log;
import com.doitbig.successway.chatx.Interfaces.Main;
import com.doitbig.successway.chatx.LiveData.FriendsDataLiveData;
import com.doitbig.successway.chatx.Models.FriendData;
import com.doitbig.successway.chatx.Models.MessagesData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class DB implements Main {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser;

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    @Override
    public FriendsDataLiveData getFriends(String location) {
        return null;
    }

    @Override
    public void postMessage(MessagesData mData) {
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

    public void setUserSignedOut()
    {
        mAuth.addAuthStateListener(firebaseAuth -> {
           mUser = firebaseAuth.getCurrentUser();
            if (mUser!=null)
            {
                long timeStamp = System.currentTimeMillis() / 1000L;
                mRef.child("Users").child(mUser.getUid()).child("Active").setValue(false);
                mRef.child("Users").child(mUser.getUid()).child("Timestamp").setValue(timeStamp);
            }

        });
    }

    public void updateItems(List<String> mItems)
    {
        mAuth.addAuthStateListener(firebaseAuth -> {
            mUser = firebaseAuth.getCurrentUser();

            for(String key:mItems)
            {
               mRef.child("Chats").child(mUser.getUid()).child(key).removeValue();
            }

        });
    }
}
