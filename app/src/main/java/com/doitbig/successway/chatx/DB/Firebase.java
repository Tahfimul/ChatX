package com.doitbig.successway.chatx.DB;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Interfaces.ForgotPassword;
import com.doitbig.successway.chatx.Interfaces.SignIn;
import com.doitbig.successway.chatx.Models.User;
import com.doitbig.successway.chatx.Interfaces.UserRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase extends AppCompatActivity implements SignIn, UserRegister, ForgotPassword {

    public MutableLiveData<Boolean> result = new MutableLiveData<>();
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;

    ExceptionMessageHandler exceptionMessageHandler = new ExceptionMessageHandler();

    @Override
    public void submitSigninData(User user) {
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(user.getmUserEmail(), user.getmUserPassword()).addOnCompleteListener(task -> {
             if(task.isSuccessful())
             {
                 mUser = mAuth.getCurrentUser();
                 if (mUser !=null)
                 {
                     if (mUser.isEmailVerified())
                     {
                         long timeStamp = System.currentTimeMillis()/1000L;
                         FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Username").setValue(mUser.getDisplayName());
                         FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Timestamp").setValue(timeStamp);
                         FirebaseDatabase.getInstance().getReference().child("Users").child(mUser.getUid()).child("Active").setValue(true);
                         result.setValue(true);
                     }

                     else
                     {
                         exceptionMessageHandler.setError("Please verify your account using link sent to your email.");
                         result.setValue(false);
                     }
                 }
                 else
                 {
                     exceptionMessageHandler.setError("Failed to retrieve user data. Try again.");
                     result.setValue(false);
                 }
             }
             else
             {
                 exceptionMessageHandler.setError(task.getException().getMessage());
                 result.setValue(false);
             }

        });
    }


    @Override
    public void submitUser(User user) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(user.getmUserEmail(), user.getmUserPassword()).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                mUser = mAuth.getCurrentUser();
                if(mUser!=null)
                {
                    mUser.sendEmailVerification();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(user.getmUserName()).build();
                    mUser.updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            result.setValue(true);
                        }
                        else
                        {
                            exceptionMessageHandler.setError(task1.getException().getMessage());
                            result.setValue(false);
                        }
                    });
                }

            }
            else
            {
                exceptionMessageHandler.setError(task.getException().getMessage());
                result.setValue(false);
            }

        });
    }

    @Override
    public void submitForgotData(String email) {

        mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                result.setValue(true);
            else
            {
                exceptionMessageHandler.setError(task.getException().getMessage());
                result.setValue(false);
            }
        });

    }

    @Override
    public MutableLiveData<Boolean> getForgotPasswordResult() {
        return result;
    }
}
