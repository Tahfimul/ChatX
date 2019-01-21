package com.doitbig.successway.chatx;

import android.util.Log;
import com.doitbig.successway.chatx.Interfaces.ExceptionMessageHandlerInterface;

public class ExceptionMessageHandler implements ExceptionMessageHandlerInterface {

    private static String Error;

    @Override
    public void setError(String error) {
        Error = error;
    }

    @Override
    public String getError() {
        return this.Error;
    }
}
