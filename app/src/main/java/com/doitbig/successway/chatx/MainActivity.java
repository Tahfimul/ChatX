package com.doitbig.successway.chatx;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.doitbig.successway.chatx.Models.ChatData;
import com.doitbig.successway.chatx.ViewModels.ChatViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textView;
    TextView textView2;
    EditText message;
    EditText user;
    Button button;

    ChatViewModel mViewModel;
    ChatData mData;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = findViewById(R.id.editText);
        user = findViewById(R.id.user);
        button = findViewById(R.id.btn);
        recyclerView = findViewById(R.id.recyclerView);

        button.setOnClickListener(this);

        mViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        mViewModel.getMessage("Messages ").observe(this, Observer ->{
            if (Observer!=null)
            {
               processData(Observer);
            }

        });

    }

    private void processData(TreeMap<String, ChatData> observer) {
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.removeAllViews();

        for (String key:observer.keySet()){
            TextView textView = new TextView(this);
            textView.setText(observer.get(key).getUser()+"\t"+observer.get(key).getVal());

            if (observer.get(key).getUser().equals("1"))
                textView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));


            else if(observer.get(key).getUser().equals("2"))
                textView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            linearLayout.addView(textView);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn:
                mViewModel.postMessage(new ChatData("", message.getText().toString(), user.getText().toString()));
                break;
        }
    }
}
