package com.doitbig.successway.chatx.Activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.doitbig.successway.chatx.Adapters.StartNewConversationSearchRecyclerViewAdapter;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Interfaces.ChatWindow;
import com.doitbig.successway.chatx.Models.FriendData;
import com.doitbig.successway.chatx.Models.UserData;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.Repos.ChatWindowRepo;
import com.doitbig.successway.chatx.ViewModels.StartNewConversationViewModel;

public class StartNewConversation extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton mSendNewMessage;
    private EditText mFriendUser;

    private StartNewConversationViewModel mViewModel;

    private ExceptionMessageHandler mException;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ProgressDialog mProgressDiag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_conversation);
        mSendNewMessage = findViewById(R.id.send_new_message_btn);
        mFriendUser = findViewById(R.id.friend_user_name);
        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerViewAdapter = new StartNewConversationSearchRecyclerViewAdapter();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        mViewModel = ViewModelProviders.of(this).get(StartNewConversationViewModel.class);

        mException = new ExceptionMessageHandler();

        mSendNewMessage.setOnClickListener(this);

        mProgressDiag = new ProgressDialog(this);
        mProgressDiag.setMessage("Retrieving data. Check your internet connection.");
        mProgressDiag.show();

        mViewModel.getAllUsers().observe(this, Observer->{
            if (Observer!=null)
            {
                mProgressDiag.dismiss();
                ((StartNewConversationSearchRecyclerViewAdapter) mRecyclerViewAdapter).setData(Observer);

            }
            else
                Toast.makeText(this, mException.getError(), Toast.LENGTH_SHORT).show();


        });

        setSearchQueryListener();

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
