package com.doitbig.successway.chatx.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Interfaces.StartNewConversation;
import com.doitbig.successway.chatx.Models.UserData;
import com.doitbig.successway.chatx.Repos.StartNewConversationRepo;

import java.util.List;

public class StartNewConversationViewModel extends ViewModel implements StartNewConversation {

    private static UserData sendMessageTo;

    StartNewConversationRepo mRepo = new StartNewConversationRepo();

    @Override
    public void SendNewMessage(String mFriendUser, String mMessage) {
            mRepo.SendNewMessage(mFriendUser, mMessage);
    }

    @Override
    public LiveData<List<UserData>> getAllUsers() {
        return mRepo.getAllUsers();
    }

//    private boolean FieldsNotEmpty(String mFriendUser, String mMessage)
//    {
//        if (mFriendUser.isEmpty()||mMessage.isEmpty())
//        {
//            new ExceptionMessageHandler().setError("Fields can't be empty!");
//            return false;
//        }
//        return true;
//    }
}
