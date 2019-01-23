package com.doitbig.successway.chatx.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.R;

import java.util.List;

public class ChatWindowRecyclerViewAdapter extends RecyclerView.Adapter {

    private class MessageSentViewHolder extends RecyclerView.ViewHolder
    {
        TextView mMessage;

        public MessageSentViewHolder(@NonNull View itemView) {
            super(itemView);
            mMessage = itemView.findViewById(R.id.message);
        }

        void bind(ChatData mMessage)
        {
            this.mMessage.setText(mMessage.getVal());
        }
    }

    public class MessageReceivedViewHolder extends RecyclerView.ViewHolder{

        TextView mMessage;

        public MessageReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            mMessage = itemView.findViewById(R.id.message);
        }

        void bind(ChatData mMessage)
        {
            this.mMessage.setText(mMessage.getVal());
        }
    }

    private static List<ChatData> mMessages;
    private final int STATE_RECEIVED_MESSAGE = 1;
    private final int STATE_SENT_MESSAGE = 0;

    public void setData(List<ChatData> mMessages)
    {
        this.mMessages = mMessages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMessages.get(position).getState()==1)
            return STATE_RECEIVED_MESSAGE;
        else
            return STATE_SENT_MESSAGE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View view;
        switch (type)
        {
            case STATE_RECEIVED_MESSAGE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_received_text, viewGroup, false);
                return new MessageReceivedViewHolder(view);
            case STATE_SENT_MESSAGE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_sent_text, viewGroup, false);
                return new MessageSentViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType())
        {
            case STATE_RECEIVED_MESSAGE:
                ((MessageReceivedViewHolder) viewHolder).bind(mMessages.get(position));
                break;
            case STATE_SENT_MESSAGE:
                ((MessageSentViewHolder) viewHolder).bind(mMessages.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (mMessages==null)
            return 0;
        return mMessages.size();
    }

}
