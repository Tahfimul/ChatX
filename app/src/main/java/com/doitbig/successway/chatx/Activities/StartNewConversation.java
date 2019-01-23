package com.doitbig.successway.chatx.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.doitbig.successway.chatx.Adapters.StartNewConversationSearchRecyclerViewAdapter;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.ViewModels.StartNewConversationViewModel;

public class StartNewConversation extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton mSendNewMessage;
    private EditText mFriendUser;
    private ProgressBar mProgressBar;

    private StartNewConversationViewModel mViewModel;

    private ExceptionMessageHandler mException;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_conversation);
        mSendNewMessage = findViewById(R.id.send_new_message_btn);
        mFriendUser = findViewById(R.id.friend_user_name);
        mRecyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progressbar);
        mProgressBar.setVisibility(View.VISIBLE);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerViewAdapter = new StartNewConversationSearchRecyclerViewAdapter();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mViewModel = ViewModelProviders.of(this).get(StartNewConversationViewModel.class);

        mException = new ExceptionMessageHandler();

        mSendNewMessage.setOnClickListener(this);

        mViewModel.getAllUsers().observe(this, Observer->{
            mProgressBar.setVisibility(View.INVISIBLE);
            if (Observer!=null)
            {
                ((StartNewConversationSearchRecyclerViewAdapter) mRecyclerViewAdapter).setData(Observer);
                setSearchQueryListener();

            }
            else
                Toast.makeText(this, mException.getError(), Toast.LENGTH_SHORT).show();


        });

    }

    private void setSearchQueryListener() {
        mFriendUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((StartNewConversationSearchRecyclerViewAdapter) mRecyclerViewAdapter).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_new_message_btn)
        {
            if (((StartNewConversationSearchRecyclerViewAdapter)mRecyclerViewAdapter).getmCurrentHighlightedUser()!=null)
                startChatWindow();
            else
                Toast.makeText(this, "Choose a user", Toast.LENGTH_SHORT).show();
        }
    }

    private void startChatWindow() {
        Intent chatWindow = new Intent(this, ChatWindowActivity.class);
        startActivity(chatWindow);
    }
}
