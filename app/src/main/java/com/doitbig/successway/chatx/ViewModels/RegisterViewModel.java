package com.doitbig.successway.chatx.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Repos.UserRegisterRepo;
import com.doitbig.successway.chatx.Models.User;
import com.doitbig.successway.chatx.Interfaces.UserRegister;

public class RegisterViewModel extends ViewModel implements UserRegister {

    UserRegisterRepo mRepo = new UserRegisterRepo();
    @Override
    public void submitUser(User user) {
        if(InputsCheck(user))
            mRepo.submitUser(user);
        else
            getStatus().setValue(false);
    }

    private boolean InputsCheck(User user) {

        ExceptionMessageHandler exceptionMessageHandler = new ExceptionMessageHandler();
        if (user.getmUserName().isEmpty()||user.getmUserEmail().isEmpty()||user.getmUserPassword().isEmpty()) {
            exceptionMessageHandler.setError("Input all fields");
            return false;
        }
        else if (user.getmUserName().length() > 20)
        {
            exceptionMessageHandler.setError("Username can't have more than 20 characters");
            return false;
        }

        return true;
    }

    public MutableLiveData<Boolean> getStatus()
    {
        return mRepo.getStatus();
    }
}
