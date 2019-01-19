package com.doitbig.successway.chatx.Repos;

import android.arch.lifecycle.MutableLiveData;
import com.doitbig.successway.chatx.DB.Firebase;
import com.doitbig.successway.chatx.Models.User;
import com.doitbig.successway.chatx.Interfaces.UserRegister;

public class UserRegisterRepo implements UserRegister {
    Firebase mDB = new Firebase();
    @Override
    public void submitUser(User user) {
        mDB.submitUser(user);
    }

    public MutableLiveData<Boolean> getStatus()
    {
        return mDB.result;
    }
}
