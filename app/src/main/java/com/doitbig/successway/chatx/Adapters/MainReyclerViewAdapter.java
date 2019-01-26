package com.doitbig.successway.chatx.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.doitbig.successway.chatx.Activities.ChatWindowActivity;
import com.doitbig.successway.chatx.Interfaces.ClickListener;
import com.doitbig.successway.chatx.Models.FriendData;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.Repos.ChatWindowRepo;
import eu.davidea.flipview.FlipView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MainReyclerViewAdapter extends RecyclerView.Adapter<MainReyclerViewAdapter.CustomViewHolder> {

    public static class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{

        private FlipView mUserSatusIndicator;
        private TextView mUserName;
        private TextView mLatestChat;

        private Context mContext;

        private FriendData mFriendData;

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        private boolean isChecked = false;

        @SuppressLint("ClickableViewAccessibility")
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserSatusIndicator = itemView.findViewById(R.id.status_indicator);
            mUserName = itemView.findViewById(R.id.user_name);
            mLatestChat = itemView.findViewById(R.id.latest_chat);

            mContext = itemView.getContext();

            itemView.setOnTouchListener(this);
        }

        private void bind(FriendData mFriendData)
        {
            this.mFriendData = mFriendData;

            setIndicatorStatus();

            mUserName.setText(mFriendData.getmUser());
            mLatestChat.setText(mFriendData.getmLatestMessage());

            this.clicklistener= new ClickListener() {
                @Override
                public void onClick() {
                    new ChatWindowRepo().setFriendUser(mFriendData);
                    Intent chatWindow = new Intent(mContext, ChatWindowActivity.class);
                    mContext.startActivity(chatWindow);
                }

                @Override
                public void onLongClick() {

                    if (isChecked)
                    {
                        isChecked = false;
                        setIndicatorStatus();
                        mUserSatusIndicator.flip(isChecked);
                    }
                    else
                    {
                        isChecked = true;
                        setCheckedState();
                        mUserSatusIndicator.flip(isChecked);
                    }

                }
            };

            gestureDetector=new GestureDetector(mContext,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    if(clicklistener!=null)
                        clicklistener.onClick();
                    return super.onSingleTapConfirmed(e);
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    if(clicklistener!=null){
                        clicklistener.onLongClick();
                    }
                }
            });
        }

        private void setIndicatorStatus() {
            if (mFriendData.isActive())
                mUserSatusIndicator.setFrontImage(R.drawable.ic_round_user_status_active);

            else
                mUserSatusIndicator.setFrontImage(R.drawable.ic_round_user_status_inactive);

        }

        private void setCheckedState()
        {
            mUserSatusIndicator.setFrontImage(R.drawable.ic_check_white);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);

            return true;
        }
    }

    private List<FriendData> mData = new ArrayList<>();

    public MainReyclerViewAdapter(){

    }
    public void setData(TreeMap<String, FriendData> mData)
    {
        convertToList(mData);
    }

    private void convertToList(TreeMap<String, FriendData> mData) {
        this.mData.clear();
        for (String key:mData.keySet())
        {
            this.mData.add(mData.get(key));
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout mLayout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_user_friend, viewGroup, false);
        return new CustomViewHolder(mLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        customViewHolder.bind(mData.get(i));
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }
}
