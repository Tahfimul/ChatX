package com.doitbig.successway.chatx.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.doitbig.successway.chatx.Interfaces.ChatWindow;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.Models.FriendData;
import com.doitbig.successway.chatx.Repos.ChatWindowRepo;

import java.util.List;

public class ChatWindowViewModel extends ViewModel implements ChatWindow {

    ChatWindowRepo mRepo = new ChatWindowRepo();

    @Override
    public LiveData<List<ChatData>> getMessages(String mFriendUser) {
        return mRepo.getMessages(mFriendUser);
    }

    @Override
    public void sendMessage(String mMessage) {
        if(!mMessage.isEmpty())
            mRepo.sendMessage(mMessage);
    }

    public FriendData getFriendUser()
    {
        FriendData mFriendData = mRepo.getFriendUser();
        if (!mFriendData.isActive())
            mFriendData.setmActiveMessage(calculateTimeMessage(mFriendData.getmTimeStamp()));
        else
            mFriendData.setmActiveMessage("Active Now");
        return mFriendData;
    }

    private String calculateTimeMessage(long mTimeStamp) {

        mTimeStamp*=1000;

       final int SECOND_MILLIS = 1000;
       final int MINUTE_MILLIS = 60*SECOND_MILLIS;
       final int HOUR_MILLIS = 60*MINUTE_MILLIS;
       final int DAY_MILLIS = 24 * HOUR_MILLIS;

       long timeNow = System.currentTimeMillis();

       final long timeDiff = timeNow - mTimeStamp;

       if (timeDiff < MINUTE_MILLIS)
           return "Just Now";
       else if(timeDiff < 2 * MINUTE_MILLIS)
           return "A minute ago";
       else if (timeDiff < 50 * MINUTE_MILLIS)
           return timeDiff/MINUTE_MILLIS + " Minutes ago";
       else if (timeDiff < 90 * MINUTE_MILLIS)
           return "An hour ago";
       else if (timeDiff < 24 * HOUR_MILLIS)
           return timeDiff / HOUR_MILLIS + " Hours ago";
       else if (timeDiff < 48 * HOUR_MILLIS)
           return "Yesterday";
       else
           return timeDiff / DAY_MILLIS + " Days ago";
    }
}
