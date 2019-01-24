package com.doitbig.successway.chatx.LiveData;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Models.FriendData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
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
                        if (mData!=null) mData.clear();
                        for(DataSnapshot dS:dataSnapshot.getChildren())
                        {
                            if(dS.child("Latest").getValue()!=null)
                                getFriendUserDetails(dS.getKey(), dS.child("Latest").getValue().toString());
                        }
                    }

                    private void getFriendUserDetails(String key, String latestChat) {
                        FirebaseDatabase.getInstance().getReference().child("Users")
                                .child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChildren()) {
                                    Log.i("FriendsLiveData()", dataSnapshot.child("Username").getValue().toString());
                                    mData.put(key, new FriendData(key, dataSnapshot.child("Username").getValue().toString(), latestChat, Boolean.valueOf(dataSnapshot.child("Active").getValue().toString())));
                                }
                                 else
                                    mData.put(key, new FriendData(key, "User_Deleted", latestChat, false));
                                setValue(mData);
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
                mException.setError("Couldn't retrieve user. Try again.");
                setValue(null);
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
