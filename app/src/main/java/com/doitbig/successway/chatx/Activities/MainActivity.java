package com.doitbig.successway.chatx.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.doitbig.successway.chatx.Adapters.MainReyclerViewAdapter;
import com.doitbig.successway.chatx.Decor.MainAdapterDecor;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.ViewModels.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private ExceptionMessageHandler mException;

    private MainViewModel mViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        mAddMessage = findViewById(R.id.add_message);
        mSignOutBtn = findViewById(R.id.sign_out_btn);
        mProgressBar = findViewById(R.id.progressbar);

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

        mAdapter = new MainReyclerViewAdapter();

        recyclerView.addItemDecoration(new MainAdapterDecor());
        recyclerView.setAdapter(mAdapter);

    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mViewModel.getFriends("Chats").observe(this, Observer ->{
            mProgressBar.setVisibility(View.INVISIBLE);
            if (Observer!=null)
                ((MainReyclerViewAdapter) mAdapter).setData(Observer);
            else
                Toast.makeText(this, mException.getError(), Toast.LENGTH_LONG).show();

        });
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
