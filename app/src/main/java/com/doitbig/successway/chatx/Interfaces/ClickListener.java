package com.doitbig.successway.chatx.Interfaces;

import android.view.MotionEvent;
import android.view.View;
import com.doitbig.successway.chatx.Models.MessagesData;

public interface ClickListener{
    public void onClick(int position);
    public void onLongClick();
    public void onLongClick(int position);
    public void onLongClick(MessagesData messagesData, int pos);
}
