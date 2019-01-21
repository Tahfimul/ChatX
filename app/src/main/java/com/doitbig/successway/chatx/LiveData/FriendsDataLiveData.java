package com.doitbig.successway.chatx.LiveData;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Models.FriendData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class FriendsDataLiveData extends LiveData<TreeMap<String, FriendData>> {

    //Firebase
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ValueEventListener mValueEventListener;
    DatabaseReference mRef;

    String mUID;

    ExceptionMessageHandler mException = new ExceptionMessageHandler();

    TreeMap<String, FriendData> mData = new TreeMap<>();

    public FriendsDataLiveData(String location)
    {
        this.mRef = FirebaseDatabase.getInstance().getReference().child(location);
    }

    @Override
    protected void onActive() {
        super.onActive();
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(firebaseAuth -> {
            mUser = firebaseAuth.getCurrentUser();
            if (mUser!=null)
            {
                mUID = mUser.getUid();
               mValueEventListener = mRef.child(mUID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dS:dataSnapshot.getChildren())
                        {
                            getFriendUserDetails(dS.getKey());
                        }
                    }

                    private void getFriendUserDetails(String key) {
                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mData.put(key, new FriendData(key, dataSnapshot.child("Username").getValue().toString(), dataSnapshot.child("Active").getValue().toString()));
                                setValue(mData);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        setValue(null);
                        mException.setError(databaseError.getMessage());
                    }
                });
                mRef.keepSynced(true);

            }
            else
            {
                setValue(null);
                mException.setError("Couldn't retrieve user. Try again.");
            }
        });
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (mValueEventListener!=null)
            mRef.removeEventListener(mValueEventListener);

    }
}
