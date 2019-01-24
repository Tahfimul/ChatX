package com.doitbig.successway.chatx.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.doitbig.successway.chatx.Adapters.ChatWindowRecyclerViewAdapter;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.ViewModels.ChatWindowViewModel;

public class ChatWindowActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ImageButton mSendBtn;
    private EditText mMessage;

    private ChatWindowViewModel mViewModel;

    private ExceptionMessageHandler mException;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        mToolbar = findViewById(R.id.toolbar);

        mRecyclerView = findViewById(R.id.recyclerView);

        mMessage = findViewById(R.id.message_txt);
        mSendBtn = findViewById(R.id.message_send);
        mSendBtn.setOnClickListener(this);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerViewAdapter = new ChatWindowRecyclerViewAdapter();
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mViewModel = ViewModelProviders.of(this).get(ChatWindowViewModel.class);

        mException = new ExceptionMessageHandler();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(mViewModel.getFriendUser().getmUser());

        mViewModel.getMessages(mViewModel.getFriendUser().getmUID()).observe(this, Observer->{
            if (Observer!=null)
            {
                getSupportActionBar().setSubtitle(Observer.getPresenceMessage());

                ((ChatWindowRecyclerViewAdapter) mRecyclerViewAdapter).setData(Observer.getMessagesData());
            }
            else
                Toast.makeText(this, mException.getError(), Toast.LENGTH_SHORT).show();
        });

        startMessageInputListener();


    }

    private void startMessageInputListener() {
        mMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0)
                    mSendBtn.setImageResource(R.drawable.ic_send_black_24dp);
                else
                    mSendBtn.setImageResource(0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.message_send)
        {
            mViewModel.sendMessage(mMessage.getText().toString());
            mMessage.getText().clear();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
