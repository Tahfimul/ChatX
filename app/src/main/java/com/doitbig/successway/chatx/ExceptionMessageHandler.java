package com.doitbig.successway.chatx;

import android.util.Log;
import com.doitbig.successway.chatx.Interfaces.ExceptionMessageHandlerInterface;

public class ExceptionMessageHandler implements ExceptionMessageHandlerInterface {

    private static String Error;

    @Override
    public void setError(String error) {
        Log.i("ExceptionMessage()", error);
        Error = error;
    }

    @Override
    public String getError() {
        Log.i("ExceptionMessage()", this.Error);
        return this.Error;
    }
}
