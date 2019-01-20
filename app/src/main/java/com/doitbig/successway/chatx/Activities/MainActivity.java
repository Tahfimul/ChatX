package com.doitbig.successway.chatx.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.doitbig.successway.chatx.Adapters.MainReyclerViewAdapter;
import com.doitbig.successway.chatx.Decor.FriendsAdapterDecor;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.ViewModels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    ExceptionMessageHandler mException;

    MainViewModel mViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mException = new ExceptionMessageHandler();
        mAdapter = new MainReyclerViewAdapter();
        recyclerView.addItemDecoration(new FriendsAdapterDecor());
        recyclerView.setAdapter(mAdapter);

        mViewModel.getFriends("Friends").observe(this, Observer ->{
            if (Observer!=null)
            {
                ((MainReyclerViewAdapter) mAdapter).setData(Observer);
            }
            else
                Toast.makeText(this, mException.getError(), Toast.LENGTH_LONG).show();

        });


    }

}
