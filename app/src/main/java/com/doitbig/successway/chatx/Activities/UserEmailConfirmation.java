package com.doitbig.successway.chatx.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.doitbig.successway.chatx.R;

public class UserEmailConfirmation extends AppCompatActivity implements View.OnClickListener{

    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_email_confirmation);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn)
            startSignIn();
    }

    private void startSignIn()
    {
        Intent signIn = new Intent(this, SignInActivity.class);
        startActivity(signIn);
    }
}
