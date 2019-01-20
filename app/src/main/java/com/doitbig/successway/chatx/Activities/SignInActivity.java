package com.doitbig.successway.chatx.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.doitbig.successway.chatx.ExceptionMessageHandler;
import com.doitbig.successway.chatx.Models.User;
import com.doitbig.successway.chatx.R;
import com.doitbig.successway.chatx.ViewModels.SignInViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn;
    Button registerBtn;
    EditText userEmail;
    EditText userPassword;
    TextView userForgotPassword;

    //Firebase
    FirebaseAuth mAuth;

    SignInViewModel mViewModel;

    ExceptionMessageHandler exception = new ExceptionMessageHandler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);
        btn = findViewById(R.id.btn);
        registerBtn = findViewById(R.id.registerBtn);
        userEmail = findViewById(R.id.user_email);
        userPassword = findViewById(R.id.user_password);
        userForgotPassword = findViewById(R.id.user_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        mViewModel = ViewModelProviders.of(this).get(SignInViewModel.class);

        btn.setOnClickListener(this);
        userForgotPassword.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

        mViewModel.getAuthResult().observe(this, Observer->{
            if (Observer!=null)
                if(Observer)
                    startMain();
                else
                    Toast.makeText(this, exception.getError(), Toast.LENGTH_LONG).show();
        });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn:
                mViewModel.submitSigninData(new User("", userEmail.getText().toString(), userPassword.getText().toString()));
                break;
            case R.id.registerBtn:
                startRegistration();
                break;
            case R.id.user_forgot_password:
                startForgotPassword();
                break;

        }

    }


    private void startMain() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    private void startRegistration()
    {
        Intent register = new Intent(this, RegistrationActivity.class);
        startActivity(register);
    }

    private void startForgotPassword()
    {
        Intent forgotPassword = new Intent(this, ForgotPasswordActivity.class);
        startActivity(forgotPassword);
    }
}
