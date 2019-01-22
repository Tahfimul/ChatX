package com.doitbig.successway.chatx.LiveData;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Models.UserData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class AllUsersLiveData extends LiveData<List<UserData>> {

    private ValueEventListener mValueEventListener;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mRef;

    private List<UserData> mUserData = new ArrayList<>();

    @Override
    protected void onActive() {
        super.onActive();
        mAuth = FirebaseAuth.getInstance();

        mAuth.addAuthStateListener(firebaseAuth -> {
            mUser = firebaseAuth.getCurrentUser();
            if (mUser!=null)
            {
                mRef = FirebaseDatabase.getInstance().getReference();
                mValueEventListener = mRef.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (mUserData!=null)
                            mUserData.clear();
                        for (DataSnapshot dS:dataSnapshot.getChildren())
                        {
                            mUserData.add(new UserData(dS.getKey(), dS.child("Username").getValue().toString()));
                        }
                        setValue(mUserData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        new ExceptionMessageHandler().setError(databaseError.getMessage());
                        setValue(null);
                    }
                });

                mRef.keepSynced(true);
            }
            else
            {
                new ExceptionMessageHandler().setError("Failed to retrieve user data. Try again!");
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
