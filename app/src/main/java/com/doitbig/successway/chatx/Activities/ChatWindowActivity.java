package com.doitbig.successway.chatx.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private Button mSendBtn;
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
        getSupportActionBar().setSubtitle(mViewModel.getFriendUser().getmStatus());

        mViewModel.getMessages(mViewModel.getFriendUser().getmUID()).observe(this, Observer->{
            if (Observer!=null)
            {
                ((ChatWindowRecyclerViewAdapter) mRecyclerViewAdapter).setData(Observer);
            }
            else
                Toast.makeText(this, mException.getError(), Toast.LENGTH_SHORT).show();
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.message_send)
        {
            mViewModel.sendMessage(mMessage.getText().toString());
        }
    }
}
