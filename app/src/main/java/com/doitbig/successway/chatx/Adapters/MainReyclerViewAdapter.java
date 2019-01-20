package com.doitbig.successway.chatx.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.doitbig.successway.chatx.Activities.ChatWindowActivity;
import com.doitbig.successway.chatx.Models.FriendData;
import com.doitbig.successway.chatx.R;
import java.util.List;

public class MainReyclerViewAdapter extends RecyclerView.Adapter<MainReyclerViewAdapter.CustomViewHolder> {

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        LinearLayout mParentLayout;
        TextView mUserName;
        TextView mUserStatus;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            mParentLayout = itemView.findViewById(R.id.parent);
            mUserName = itemView.findViewById(R.id.user_name);
            mUserStatus = itemView.findViewById(R.id.user_status);
        }

    }

    private List<FriendData> mData;

    public MainReyclerViewAdapter(){

    }
    public void setData(List<FriendData> mData)
    {
        this.mData = mData;
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
        customViewHolder.mUserName.setText(mData.get(i).getmUser());
        customViewHolder.mUserStatus.setText(mData.get(i).getmStatus());
        customViewHolder.mParentLayout.setOnClickListener(v -> {
            Intent chatWindow = new Intent(customViewHolder.itemView.getContext(), ChatWindowActivity.class);
            chatWindow.putExtra("user_id", mData.get(i).getmUser());
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
