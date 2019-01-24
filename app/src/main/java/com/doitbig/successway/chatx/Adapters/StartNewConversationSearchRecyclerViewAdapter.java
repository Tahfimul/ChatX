package com.doitbig.successway.chatx.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.doitbig.successway.chatx.Models.FriendData;
import com.doitbig.successway.chatx.Models.UserData;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.Repos.ChatWindowRepo;

import java.util.ArrayList;
import java.util.List;

public class StartNewConversationSearchRecyclerViewAdapter extends RecyclerView.Adapter implements Filterable {

    private class CustomViewHolder extends RecyclerView.ViewHolder{

        ImageView mUserStatusIndicator;
        TextView mUsername;
        ImageView mCheckBox;
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            mUsername = itemView.findViewById(R.id.user_name);
            mUserStatusIndicator = itemView.findViewById(R.id.status_indicator);
            mCheckBox = itemView.findViewById(R.id.checkbox);
        }

        void bind(UserData mUser)
        {
            mUsername.setText(mUser.getmUsername());
            setIsHighlighted(mUser);
            setStatusIndicator(mUser);
        }

        private void setIsHighlighted(UserData mUser) {
            if (mUser.ismHighlighted())
                mCheckBox.setImageResource(R.drawable.ic_check);
            else
                mCheckBox.setImageResource(0);
        }

        private void setStatusIndicator(UserData mUser) {
            if (mUser.isUserActive())
                mUserStatusIndicator.setImageResource(R.drawable.ic_flat_user_status_active);
            else
                mUserStatusIndicator.setImageResource(R.drawable.ic_flat_user_status_inactive);
        }
    }

    private List<UserData> mUsers;
    private List<UserData> mUsersQueryList;

    private UserData mCurrentHighlightedUser;
    private int mCurrentHighLightedPosition;


    public void setData(List<UserData> mUsers)
    {
        this.mUsers = mUsers;
        this.mUsersQueryList = new ArrayList<>(mUsers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_users_list_new_conversation, viewGroup,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((CustomViewHolder) viewHolder).bind(mUsers.get(i));

        ((CustomViewHolder) viewHolder).mUsername.setOnClickListener(v->{
            if (mCurrentHighlightedUser!=null) {

                if ((!mCurrentHighlightedUser.getmUsername().equals(mUsers.get(i).getmUsername())||mCurrentHighlightedUser.getmUsername().equals(mUsers.get(i).getmUsername()))&&mCurrentHighLightedPosition!=i)
                {
                    mCurrentHighlightedUser.setmHighlighted(false);
                    mCurrentHighlightedUser = mUsers.get(i);
                    mCurrentHighlightedUser.setmHighlighted(true);
                    mCurrentHighLightedPosition = i;
                    new ChatWindowRepo().setFriendUser((new FriendData(mCurrentHighlightedUser.getmUID(), mCurrentHighlightedUser.getmUsername(), "" ,mCurrentHighlightedUser.isUserActive())));
                }
                else if (mCurrentHighlightedUser.getmUsername().equals(mUsers.get(i).getmUsername())&&mCurrentHighLightedPosition==i) {
                    mCurrentHighlightedUser.setmHighlighted(false);
                    mCurrentHighlightedUser = null;
                    mCurrentHighLightedPosition = -1;
                }
            }
            else
            {
                mUsers.get(i).setmHighlighted(true);
                mCurrentHighlightedUser = mUsers.get(i);
                mCurrentHighLightedPosition = i;
                new ChatWindowRepo().setFriendUser(new FriendData(mCurrentHighlightedUser.getmUID(), mCurrentHighlightedUser.getmUsername(), "" ,mCurrentHighlightedUser.isUserActive()));
            }

            notifyDataSetChanged();

        });
    }

    @Override
    public int getItemCount() {
        if (mUsers==null)
            return 0;
        return mUsers.size();
    }

    @Override
    public Filter getFilter() {
        return mUsersFilter;
    }

    private Filter mUsersFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UserData> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0)
                filteredList.addAll(mUsersQueryList);
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(UserData mItem:mUsersQueryList)
                {
                    if (mItem.getmUsername().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(mItem);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mUsers.clear();
            mUsers.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public UserData getmCurrentHighlightedUser()
    {
        return mCurrentHighlightedUser;
    }


}
