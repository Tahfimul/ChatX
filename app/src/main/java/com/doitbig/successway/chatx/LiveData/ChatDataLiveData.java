package com.doitbig.successway.chatx.LiveData;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.doitbig.successway.chatx.DB.Firebase;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class ChatDataLiveData extends LiveData<List<ChatData>> {

    //Firebase Auth
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String mUserId;
    String mFriendUser;

    DatabaseReference mRef;
    ValueEventListener mEventLisenter;

    ExceptionMessageHandler mException = new ExceptionMessageHandler();

    private List<ChatData> mMessages = new ArrayList<>();
    public ChatDataLiveData(String mFriendUser)
    {
        mRef = FirebaseDatabase.getInstance().getReference();
        this.mFriendUser = mFriendUser;
    }

    @Override
    protected void onActive() {
        super.onActive();
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(firebaseAuth -> {
            mUser = firebaseAuth.getCurrentUser();
            if (mUser!=null)
            {
                mUserId = mUser.getUid();
                mEventLisenter = mRef.child("Chats").child(mUserId).child(mFriendUser).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (mMessages!=null)
                            mMessages.clear();
                        for(DataSnapshot dS: dataSnapshot.getChildren())
                        {
                            for(DataSnapshot Ds: dS.getChildren())
                            {
                                mMessages.add(new ChatData(dS.getKey(),Ds.getValue().toString(), Ds.getKey()));
                            }
                        }
                        setValue(mMessages);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mException.setError(databaseError.getMessage());
                        setValue(null);
                    }
                });
            }

            else
            {
                mException.setError("Couldn't retrieve user data. Try again.");
                setValue(null);
            }
        });

        mRef.keepSynced(true);

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (mEventLisenter!=null)
            mRef.removeEventListener(mEventLisenter);
    }

}
