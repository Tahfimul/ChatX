package com.doitbig.successway.chatx.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class ReyclerViewAdapter extends RecyclerView.Adapter<ReyclerViewAdapter.CustomViewHolder> {

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
