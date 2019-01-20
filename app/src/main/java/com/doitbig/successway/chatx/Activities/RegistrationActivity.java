package com.doitbig.successway.chatx.Activities;

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
import com.doitbig.successway.chatx.Models.User;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.ViewModels.RegisterViewModel;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    Button register;
    Button signInBtn;
    EditText userName;
    EditText userEmail;
    EditText userPassword;

    RegisterViewModel mViewModel;

    ExceptionMessageHandler exceptionMessageHandler = new ExceptionMessageHandler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        register = findViewById(R.id.btn);
        signInBtn = findViewById(R.id.signInBtn);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        mViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        mViewModel.getStatus().observe(this, Observer->{
            if (Observer!=null)
            {
                if (Observer)
                    startUserConfirmation();
                else
                    Toast.makeText(this, exceptionMessageHandler.getError(), Toast.LENGTH_LONG).show();
            }
        });

        register.setOnClickListener(this);
        signInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:

                String mUserName = userName.getText().toString();
                String mUserEmail = userEmail.getText().toString();
                String mUserPassword = userPassword.getText().toString();

                mViewModel.submitUser(new User(mUserName, mUserEmail, mUserPassword));
                break;
            case R.id.signInBtn:
                startSignIn();
                break;

        }
    }

    private void startSignIn()
    {
        onBackPressed();
    }

    private void startUserConfirmation() {
        Intent userConfirmation = new Intent(this, UserEmailConfirmation.class);
        startActivity(userConfirmation);
    }
}
