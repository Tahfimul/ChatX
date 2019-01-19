package com.doitbig.successway.chatx.LiveData;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class ChatDataLiveData extends LiveData<TreeMap<String, ChatData>> {

    //Firebase Auth
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String mUserId;

    DatabaseReference mRef;
    ValueEventListener mEventLisenter;

    TreeMap<String, ChatData> hm = new TreeMap<>();
    public ChatDataLiveData(DatabaseReference mRef)
    {
        this.mRef = mRef;
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
                long timeStamp = System.currentTimeMillis()/1000L;
                FirebaseDatabase.getInstance().getReference().child("Seen").child(mUserId).setValue(timeStamp);
                FirebaseDatabase.getInstance().getReference().child("Users").child(mUserId).setValue("Active");
                mEventLisenter = mRef.child(mUserId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        hm.clear();
                        for(DataSnapshot dS: dataSnapshot.getChildren())
                        {
                            for(DataSnapshot Ds: dS.getChildren())
                            {
                                hm.put(dS.getKey(), new ChatData(dS.getKey(),Ds.getValue().toString(), Ds.getKey()));
                            }
                        }
                        setValue(hm);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            else
                Log.i("ChatLiveData()", "Error");
        });



        mRef.keepSynced(true);

    }

    @Override
    protected void onInactive() {
        super.onInactive();
        mRef.removeEventListener(mEventLisenter);
    }

}
