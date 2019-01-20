package com.doitbig.successway.chatx.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Interfaces.ForgotPassword;
import com.doitbig.successway.chatx.Repos.ForgotPasswordRepo;

public class ForgotPasswordViewModel extends ViewModel implements ForgotPassword {

    ForgotPasswordRepo mRepo = new ForgotPasswordRepo();

    ExceptionMessageHandler mException = new ExceptionMessageHandler();

    @Override
    public void submitForgotData(String email) {
        if(EmailNotEmpty(email))
         mRepo.submitForgotData(email);
        else
            getForgotPasswordResult().setValue(false);
    }

    @Override
    public MutableLiveData<Boolean> getForgotPasswordResult() {
        return mRepo.getForgotPasswordResult();
    }

    private boolean EmailNotEmpty(String email)
    {
        if (email.isEmpty())
        {
            mException.setError("Fill in all fields.");
            return false;
        }
        return true;
    }


}
