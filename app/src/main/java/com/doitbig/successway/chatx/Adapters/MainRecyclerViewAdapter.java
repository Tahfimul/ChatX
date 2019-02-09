package com.doitbig.successway.chatx.Adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.doitbig.successway.chatx.Models.FriendData;
import com.doitbig.successway.chatx.Models.FriendMainAdapterData;
import com.doitbig.successway.chatx.R;
import eu.davidea.flipview.FlipView;

import java.util.*;

public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.CustomViewHolder> {

    public static class CustomViewHolder extends RecyclerView.ViewHolder{

        private FlipView mUserSatusIndicator;
        private TextView mUserName;
        private TextView mLatestChat;

        private FriendMainAdapterData mFriendData;

        @SuppressLint("ClickableViewAccessibility")
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserSatusIndicator = itemView.findViewById(R.id.status_indicator);
            mUserName = itemView.findViewById(R.id.user_name);
            mLatestChat = itemView.findViewById(R.id.latest_chat);
        }

        private void bind(FriendMainAdapterData mFriendData)
        {
            this.mFriendData = mFriendData;

            if (mFriendData.ismIsChecked())
            {
                setCheckedState();
                mUserSatusIndicator.flip(true);
            }
            else
            {
                setIndicatorStatus();
                mUserSatusIndicator.flip(false);
            }

            this.mFriendData = mFriendData;

            mUserName.setText(mFriendData.getmUser());
            mLatestChat.setText(mFriendData.getmLatestMessage());
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
    }

        public static List<FriendMainAdapterData> mData = new ArrayList<>();
    private TreeMap<String, FriendMainAdapterData> mFriendDataTree = new TreeMap<>();

    private List<String> mDeletedItems = new ArrayList<>();

    public MainRecyclerViewAdapter(){

    }

    public void setData(TreeMap<String, FriendData> mData)
    {
        convertToList(mData);
    }
    private void convertToList(TreeMap<String, FriendData> mData) {

        Set<String> keys = mData.keySet();

        for (String key:keys)
        {

          FriendData mFriendData = mData.get(key);
          mFriendDataTree.put(key, new FriendMainAdapterData(mFriendData.getmUID(), mFriendData.getmUser(), mFriendData.getmLatestMessage(), mFriendData.isActive()));

        }

        for (FriendMainAdapterData data : this.mData)
        {
            if (keys.contains(data.getmUID())) {
                mFriendDataTree.get(data.getmUID()).setmIsChecked(data.ismIsChecked());
            }
        }

        this.mData.clear();
        for (String key : mFriendDataTree.keySet()) {
            this.mData.add(mFriendDataTree.get(key));
        }

        notifyDataSetChanged();
    }

    public int getSelectedItemsCount()
    {
        int count = 0;
        for (FriendMainAdapterData mData: this.mData)
        {
            if (mData.ismIsChecked())
                count++;
        }
        return count;
    }


    public void deleteSelectedItems()
    {
        mDeletedItems.clear();
        List<FriendMainAdapterData> mTempData = new ArrayList<>();
        for (FriendMainAdapterData mData: this.mData)
        {
            if (!mData.ismIsChecked()) {
                System.out.println(mData.getmUser());
                mTempData.add(mData);
            }
            else
            {
                deleteFromTreeMap(mData.getmUID());
                mDeletedItems.add(mData.getmUID());
            }
        }
        this.mData.clear();
        mData = mTempData;
        notifyDataSetChanged();
    }

    private void deleteFromTreeMap(String UID) {
        for(String item: mFriendDataTree.keySet())
        {
            if (item.equals(UID))
                mFriendDataTree.remove(item);
        }
    }

    public List<String> getDeletedItems()
    {
        return mDeletedItems;
    }

    public List<FriendData> getAllItems()
    {
        List<FriendData> mData = new ArrayList<>();
        for (FriendMainAdapterData item:this.mData)
        {
            mData.add(new FriendData(item.getmUID(), item.getmUser(), item.getmLatestMessage(), item.isActive()));
        }
        return mData;
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
