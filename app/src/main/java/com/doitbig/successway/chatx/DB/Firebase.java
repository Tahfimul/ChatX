package com.doitbig.successway.chatx.DB;

import android.arch.lifecycle.MutableLiveData;
import android.support.v7.app.AppCompatActivity;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Interfaces.SignIn;
import com.doitbig.successway.chatx.Models.User;
import com.doitbig.successway.chatx.Interfaces.UserRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Firebase extends AppCompatActivity implements SignIn, UserRegister {

    public MutableLiveData<Boolean> result = new MutableLiveData<>();
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    ExceptionMessageHandler exceptionMessageHandler = new ExceptionMessageHandler();

    @Override
    public void submitSigninData(User user) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(user.getmUserEmail(), user.getmUserPassword()).addOnCompleteListener(task -> {
             if(task.isSuccessful())
             {
                 mUser = mAuth.getCurrentUser();
                 if (mUser !=null)
                 {
                     if (mUser.isEmailVerified())
                         result.setValue(true);
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
}
