package com.doitbig.successway.chatx.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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
import com.doitbig.successway.chatx.Dialogs.MessageSelectedDialog;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Interfaces.ClickListener;
import com.doitbig.successway.chatx.Models.MessagesData;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.RecyclerViewTouch.ChatWindowRecyclerTouchListener;
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

        mException = new ExceptionMessageHandler();

        init();


    }

    private void init() {

        setUpRecyclerView();

        setUpViewModel();

        setUpToolBar();

        startMessageInputListener();

    }

    private void setUpRecyclerView() {
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerViewAdapter = new ChatWindowRecyclerViewAdapter();
        mRecyclerView.addOnItemTouchListener(new ChatWindowRecyclerTouchListener(this, mRecyclerView, new ClickListener(){

            @Override
            public void onClick(int position) {
                Toast.makeText(ChatWindowActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick() {

            }

            @Override
            public void onLongClick(int position) {

            }

            @Override
            public void onLongClick(MessagesData mMessagesData, int pos) {
                MessageSelectedDialog diag = new MessageSelectedDialog(mMessagesData, pos);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                diag.show(fragmentTransaction, "Hello");
            }
        }));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

    }

    private void setUpViewModel()
    {
        mViewModel = ViewModelProviders.of(this).get(ChatWindowViewModel.class);

        mViewModel.getMessages(mViewModel.getFriendUser().getmUID()).observe(this, Observer->{
            if (Observer!=null)
            {
                getSupportActionBar().setSubtitle(Observer.getPresenceMessage());

                ((ChatWindowRecyclerViewAdapter) mRecyclerViewAdapter).setData(Observer.getMessagesData());
            }
            else
                Toast.makeText(this, mException.getError(), Toast.LENGTH_SHORT).show();
        });
    }

    private void setUpToolBar()
    {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(mViewModel.getFriendUser().getmUser());
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
