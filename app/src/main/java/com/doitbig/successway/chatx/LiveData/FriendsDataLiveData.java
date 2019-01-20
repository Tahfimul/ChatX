package com.doitbig.successway.chatx.LiveData;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Models.FriendData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FriendsDataLiveData extends LiveData<List<FriendData>> {

    //Firebase
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ValueEventListener mValueEventListener;
    DatabaseReference mRef;

    String mUID;

    ExceptionMessageHandler mException = new ExceptionMessageHandler();

    List<FriendData> mData = new ArrayList<>();

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
                mRef.child(mUID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mData.clear();
                        for(DataSnapshot dS:dataSnapshot.getChildren())
                        {
                            mData.add(new FriendData(dS.getKey(), dS.getValue().toString()));
                        }
                        setValue(mData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        setValue(null);
                        mException.setError(databaseError.getMessage());
                    }
                });

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
