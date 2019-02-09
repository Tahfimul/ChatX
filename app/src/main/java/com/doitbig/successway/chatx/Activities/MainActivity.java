package com.doitbig.successway.chatx.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.doitbig.successway.chatx.Adapters.MainRecyclerViewAdapter;
import com.doitbig.successway.chatx.Decor.MainAdapterDecor;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Interfaces.ClickListener;
import com.doitbig.successway.chatx.Models.FriendData;
import com.doitbig.successway.chatx.Models.FriendMainAdapterData;
import com.doitbig.successway.chatx.Models.MessagesData;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.RecyclerViewTouch.MainRecyclerTouchListener;
import com.doitbig.successway.chatx.Repos.ChatWindowRepo;
import com.doitbig.successway.chatx.ViewModels.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private ExceptionMessageHandler mException;

    private MainViewModel mViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Toolbar mToolbar;
    private FloatingActionButton mAddMessage;
    private Button mSignOutBtn;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null)
            init();
        else
        {
            startSignIn();
            finish();
        }
    }

    private void init()
    {
        mToolbar = findViewById(R.id.toolbar);

        mAddMessage = findViewById(R.id.add_message);
        mSignOutBtn = findViewById(R.id.sign_out_btn);
        mProgressBar = findViewById(R.id.progressbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);

        mProgressBar.setVisibility(View.VISIBLE);

        mAddMessage.setOnClickListener(this);
        mSignOutBtn.setOnClickListener(this);

        mException = new ExceptionMessageHandler();

        setUpRecyclerView();

        initViewModel();
    }

    private void setUpRecyclerView() {

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MainRecyclerViewAdapter();

        recyclerView.addItemDecoration(new MainAdapterDecor());
        recyclerView.addOnItemTouchListener(new MainRecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(int position) {
                FriendMainAdapterData mFriendData = ((MainRecyclerViewAdapter)mAdapter).mData.get(position);
                new ChatWindowRepo().setFriendUser(new FriendData(mFriendData.getmUID(), mFriendData.getmUser(), mFriendData.getmLatestMessage(), mFriendData.isActive()));
                Intent chatWindow = new Intent(MainActivity.this, ChatWindowActivity.class);
                startActivity(chatWindow);
            }

            @Override
            public void onLongClick() {

            }

            @Override
            public void onLongClick(int position) {

                FriendMainAdapterData mData = ((MainRecyclerViewAdapter)mAdapter).mData.get(position);
                if (mData.ismIsChecked()) {
                    mData.setmIsChecked(false);
                }
                else
                    mData.setmIsChecked(true);

                mAdapter.notifyDataSetChanged();

                toggleActionBar();
            }

            @Override
            public void onLongClick(MessagesData messagesData, int pos) {

            }

            private void toggleActionBar() {
                int count = ((MainRecyclerViewAdapter)mAdapter).getSelectedItemsCount();
                if (count>0)
                {
                    setItemsSelectedActionBar(count);
                }
                else
                {
                    setItemsUnselectedActionBar();
                }

            }

        }));

        recyclerView.setAdapter(mAdapter);

    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mViewModel.getFriends("Chats").observe(this, Observer ->{
            mProgressBar.setVisibility(View.INVISIBLE);
            if (Observer!=null)
                ((MainRecyclerViewAdapter) mAdapter).setData(Observer);

            else
                Toast.makeText(this, mException.getError(), Toast.LENGTH_LONG).show();

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item_delete:
                ((MainRecyclerViewAdapter)mAdapter).deleteSelectedItems();
                mViewModel.updateItems(((MainRecyclerViewAdapter)mAdapter).getDeletedItems());
                setItemsUnselectedActionBar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setItemsSelectedActionBar(int count) {
        mToolbar.setBackgroundResource(R.color.colorGrey);
        getSupportActionBar().setTitle(String.valueOf(count));
        if (!mToolbar.getMenu().hasVisibleItems())
            mToolbar.inflateMenu(R.menu.main_action_bar_menu);
    }

    private void setItemsUnselectedActionBar() {
        mToolbar.setBackgroundResource(R.color.colorWhite);
        getSupportActionBar().setTitle(R.string.app_name);
        mToolbar.getMenu().clear();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.add_message:
                startNewConversation();
                break;
            case R.id.sign_out_btn:
                mViewModel.setUserSignedOut();
                signOut();
                startSignIn();
                finish();
                break;
        }
    }

    private void signOut() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

    private void startNewConversation() {
        Intent newConversation = new Intent(this, StartNewConversation.class);
        startActivity(newConversation);
    }

    private void startSignIn()
    {
        Intent signIn = new Intent(this, SignInActivity.class);
        startActivity(signIn);
    }
}
