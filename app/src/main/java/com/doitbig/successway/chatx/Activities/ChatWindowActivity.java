package com.doitbig.successway.chatx.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.ViewModels.ChatWindowViewModel;

public class ChatWindowActivity extends AppCompatActivity {

    private String mFriendUsername;

    private Toolbar mToolbar;

    private ChatWindowViewModel mViewModel;

    private ExceptionMessageHandler mException;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        mToolbar = findViewById(R.id.toolbar);

        mFriendUsername = getIntent().getStringExtra("user_id");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(mFriendUsername);

        mException = new ExceptionMessageHandler();

        mViewModel = ViewModelProviders.of(this).get(ChatWindowViewModel.class);
        mViewModel.getMessages(mFriendUsername).observe(this, Observer->{
            if (Observer!=null)
            {

            }
            else
                Toast.makeText(this, mException.getError(), Toast.LENGTH_SHORT).show();
        });


    }
}
