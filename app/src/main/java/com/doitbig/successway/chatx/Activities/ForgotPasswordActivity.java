package com.doitbig.successway.chatx.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.ViewModels.ForgotPasswordViewModel;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mUserEmail;
    Button mUserSubmit;
    Button mUserSignIn;

    ForgotPasswordViewModel mViewModel;

    ExceptionMessageHandler mException = new ExceptionMessageHandler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mUserEmail = findViewById(R.id.user_email);
        mUserSubmit = findViewById(R.id.btn);
        mUserSignIn = findViewById(R.id.signInBtn);

        mViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel.class);

        mUserSubmit.setOnClickListener(this);
        mUserSignIn.setOnClickListener(this);

        mViewModel.getForgotPasswordResult().observe(this, Observer ->{
            if (Observer!=null)
            {
                if (Observer)
                    startConfirmation();
                else
                    Toast.makeText(this, mException.getError(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn:
                mViewModel.submitForgotData(mUserEmail.getText().toString());

        }
    }

    private void startConfirmation() {
        Intent confirmation = new Intent(this, UserEmailConfirmation.class);
        startActivity(confirmation);
    }
}
