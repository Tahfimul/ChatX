package com.doitbig.successway.chatx.Repos;

import android.arch.lifecycle.MutableLiveData;
import com.doitbig.successway.chatx.DB.Firebase;
import com.doitbig.successway.chatx.Interfaces.ForgotPassword;

public class ForgotPasswordRepo implements ForgotPassword {

    Firebase mDB = new Firebase();

    @Override
    public void submitForgotData(String email) {
        mDB.submitForgotData(email);
    }

    @Override
    public MutableLiveData<Boolean> getForgotPasswordResult() {
        return mDB.getForgotPasswordResult();
    }
}
