package com.doitbig.successway.chatx.Decor;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainAdapterDecor extends RecyclerView.ItemDecoration {


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = 10;
        outRect.bottom = 10;
        outRect.left = 40;
        outRect.right = 40;

    }
}
