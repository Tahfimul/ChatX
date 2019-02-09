package com.doitbig.successway.chatx.RecyclerViewTouch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.doitbig.successway.chatx.Adapters.ChatWindowRecyclerViewAdapter;
import com.doitbig.successway.chatx.Interfaces.ClickListener;
import com.doitbig.successway.chatx.Models.MessagesData;
import com.doitbig.successway.chatx.R;

public class ChatWindowRecyclerTouchListener implements RecyclerView.OnItemTouchListener {

    private ClickListener mClickListener;
    private GestureDetector mGestureDetector;

    public ChatWindowRecyclerTouchListener(Context context, RecyclerView recyclerView, ClickListener clickListener)
    {
        mClickListener = clickListener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);

                View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (child!=null)
                {
                    int pos = recyclerView.getChildLayoutPosition(child);
                    MessagesData mMessagesData = ((ChatWindowRecyclerViewAdapter) recyclerView.getAdapter()).getItemByPosition(pos);
                    if (mClickListener != null)
                        mClickListener.onLongClick(mMessagesData, pos);
                }
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        if(mClickListener!=null&&mGestureDetector.onTouchEvent(motionEvent))
            mClickListener.onClick(1);

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
