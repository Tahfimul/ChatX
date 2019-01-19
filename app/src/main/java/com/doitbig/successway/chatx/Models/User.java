package com.doitbig.successway.chatx.Models;

public class User {

    String mUserName;
    String mUserEmail;
    String mUserPassword;

    public User()
    {

    }

    public User(String UserName, String UserEmail, String UserPassword)
    {
        this.mUserName = UserName;
        this.mUserEmail = UserEmail;
        this.mUserPassword = UserPassword;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserEmail() {
        return mUserEmail;
    }

    public void setmUserEmail(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    public String getmUserPassword() {
        return mUserPassword;
    }

    public void setmUserPassword(String mUserPassword) {
        this.mUserPassword = mUserPassword;
    }
}
