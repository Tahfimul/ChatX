package com.doitbig.successway.chatx.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.doitbig.successway.chatx.Activities.ChatWindowActivity;
import com.doitbig.successway.chatx.Models.FriendData;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.Repos.ChatWindowRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class MainReyclerViewAdapter extends RecyclerView.Adapter<MainReyclerViewAdapter.CustomViewHolder> {

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mParentLayout;
        ImageView mUserSatusIndicator;
        TextView mUserName;
        TextView mLatestChat;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            mParentLayout = itemView.findViewById(R.id.parent);
            mUserSatusIndicator = itemView.findViewById(R.id.status_indicator);
            mUserName = itemView.findViewById(R.id.user_name);
            mLatestChat = itemView.findViewById(R.id.latest_chat);
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

        if (mData.get(i).isActive())
            customViewHolder.mUserSatusIndicator.setBackgroundResource(R.drawable.ic_round_user_status_active);
        else
            customViewHolder.mUserSatusIndicator.setBackgroundResource(R.drawable.ic_round_user_status_inactive);

        customViewHolder.mUserName.setText(mData.get(i).getmUser());
        customViewHolder.mLatestChat.setText(mData.get(i).getmLatestMessage());
        customViewHolder.mParentLayout.setOnClickListener(v -> {
            new ChatWindowRepo().setFriendUser(mData.get(i));
            Intent chatWindow = new Intent(customViewHolder.itemView.getContext(), ChatWindowActivity.class);
            customViewHolder.itemView.getContext().startActivity(chatWindow);

        });
    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }
}
