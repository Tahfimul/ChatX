package com.doitbig.successway.chatx.LiveData;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.Models.MessagesData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ChatDataLiveData extends LiveData<ChatData> {

    //Firebase Auth
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String mUserId;
    String mFriendUser;

    DatabaseReference mRef;
    ValueEventListener mEventLisenter;

    ExceptionMessageHandler mException = new ExceptionMessageHandler();

    private List<MessagesData> mMessages = new ArrayList<>();
    private String mPresenceMessage;

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
                                    mMessages.add(new MessagesData(0, Long.valueOf(dS.getKey()),Ds.getValue().toString(), Ds.getKey()));
                                else
                                    mMessages.add(new MessagesData(1, Long.valueOf(dS.getKey()),Ds.getValue().toString(), Ds.getKey()));

                                getFriendPresence();

                            }
                        }
                    }

                    private void getFriendPresence() {
                        mRef.child("Users").child(mFriendUser).child("Timestamp").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null)
                                    mPresenceMessage = calculateTimeMessage(Long.valueOf(dataSnapshot.getValue().toString()));
                                else
                                    mPresenceMessage = "null";
                                setValue(new ChatData(mPresenceMessage, mMessages));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
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

    private String calculateTimeMessage(long mTimeStamp) {

        mTimeStamp*=1000;

        final int SECOND_MILLIS = 1000;
        final int MINUTE_MILLIS = 60*SECOND_MILLIS;
        final int HOUR_MILLIS = 60*MINUTE_MILLIS;
        final int DAY_MILLIS = 24 * HOUR_MILLIS;

        long timeNow = System.currentTimeMillis();

        final long timeDiff = timeNow - mTimeStamp;

        if (timeDiff < MINUTE_MILLIS)
            return "Just Now";
        else if(timeDiff < 2 * MINUTE_MILLIS)
            return "A minute ago";
        else if (timeDiff < 50 * MINUTE_MILLIS)
            return timeDiff/MINUTE_MILLIS + " Minutes ago";
        else if (timeDiff < 90 * MINUTE_MILLIS)
            return "An hour ago";
        else if (timeDiff < 24 * HOUR_MILLIS)
            return timeDiff / HOUR_MILLIS + " Hours ago";
        else if (timeDiff < 48 * HOUR_MILLIS)
            return "Yesterday";
        else
            return timeDiff / DAY_MILLIS + " Days ago";
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (mEventLisenter!=null)
            mRef.removeEventListener(mEventLisenter);
    }

}
