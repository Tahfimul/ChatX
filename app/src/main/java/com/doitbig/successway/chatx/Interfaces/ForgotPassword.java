package com.doitbig.successway.chatx.Interfaces;

import android.arch.lifecycle.MutableLiveData;

public interface ForgotPassword{
    public void submitForgotData(String email);

    public MutableLiveData<Boolean> getForgotPasswordResult();
}
