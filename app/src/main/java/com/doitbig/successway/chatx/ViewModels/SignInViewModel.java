package com.doitbig.successway.chatx.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Interfaces.SignIn;
import com.doitbig.successway.chatx.Repos.SignInRepo;
import com.doitbig.successway.chatx.Models.User;

public class SignInViewModel extends ViewModel implements SignIn {

    SignInRepo mRepo = new SignInRepo();

    @Override
    public void submitSigninData(User user) {
        if(InputsNotEmpty(user))
            mRepo.submitSigninData(user);
        else
            getAuthResult().setValue(false);

    }

    public MutableLiveData<Boolean> getAuthResult()
    {
        return mRepo.getUserAuthResult();
    }

    private boolean InputsNotEmpty(User user)
    {
        if(user.getmUserEmail().isEmpty()||user.getmUserPassword().isEmpty())
        {
            ExceptionMessageHandler exceptionMessageHandler = new ExceptionMessageHandler();
            exceptionMessageHandler.setError("Input all fields");
            return false;
        }

        return true;
    }
}
