package com.doitbig.successway.chatx.Repos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.doitbig.successway.chatx.DB.Firebase;
import com.doitbig.successway.chatx.Interfaces.SignIn;
import com.doitbig.successway.chatx.Models.User;

public class SignInRepo implements SignIn {

    Firebase mDB = new Firebase();

    @Override
    public void submitSigninData(User user) {
        mDB.submitSigninData(user);
    }

    public MutableLiveData<Boolean> getUserAuthResult()
    {
        return mDB.result;
    }
}
