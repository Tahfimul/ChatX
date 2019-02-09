package com.doitbig.successway.chatx.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;
import com.doitbig.successway.chatx.Adapters.ChatWindowRecyclerViewAdapter;
import com.doitbig.successway.chatx.Models.MessagesData;
import com.doitbig.successway.chatx.R;

public class MessageSelectedDialog extends DialogFragment implements View.OnClickListener {

    private MessagesData messagesData;
    private int pos;

    public MessageSelectedDialog()
    {

    }

    @SuppressLint("ValidFragment")
    public MessageSelectedDialog(MessagesData messagesData, int pos)
    {
        this.messagesData = messagesData;
        this.pos = pos;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MessageSelectedDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, viewGroup, savedInstanceState);
        View view = inflater.inflate(R.layout.layout_fragment_messages_onclick_menu, viewGroup,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView copyMsg = view.findViewById(R.id.item_copy);
        TextView delText = view.findViewById(R.id.item_delete);

        copyMsg.setOnClickListener(this);
        delText.setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog diag = getDialog();
        if (diag!=null)
        {
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;

            diag.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.item_delete:
                ChatWindowRecyclerViewAdapter mAdapter = new ChatWindowRecyclerViewAdapter();
                mAdapter.removeItemByPosition(pos);
                //Call method to remove message from DB
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;
            case R.id.item_copy:
                String msg = messagesData.getVal();
                copyMsg(msg);
                dismiss();
                break;
        }

    }

    private void copyMsg(String msg) {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("msg", msg);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getContext(), "Text Copied", Toast.LENGTH_SHORT).show();
    }
}
