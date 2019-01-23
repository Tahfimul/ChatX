package com.doitbig.successway.chatx.LiveData;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Models.ChatData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

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
                                if (Ds.getKey().equals(mUserId))
                                    mMessages.add(new ChatData(0, dS.getKey(),Ds.getValue().toString(), Ds.getKey()));
                                else
                                    mMessages.add(new ChatData(1, dS.getKey(),Ds.getValue().toString(), Ds.getKey()));

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

                mRef.keepSynced(true);
            }

            else
            {
                mException.setError("Couldn't retrieve user data. Try again.");
                setValue(null);
            }
        });


    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (mEventLisenter!=null)
            mRef.removeEventListener(mEventLisenter);
    }

}
