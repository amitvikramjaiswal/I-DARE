package com.opensource.app.idare.utils;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.opensource.app.idare.pojo.common.IDAREError;

/**
 * Created by akokala on 10/31/2017.
 */

public class IDAREErrorWrapper extends Throwable {

    @SerializedName("error")
    private IDAREError idareError;
    private Exception exception;
    private String TAG = IDAREErrorWrapper.class.getSimpleName();

    public IDAREErrorWrapper(IDAREError idareError, Exception exception) {
        this.idareError = idareError;
        this.exception = exception;

        // Ensure error message is mandatory for creating TRPErrorWrapper object
        if (idareError == null || idareError.getMessage() == null || idareError.getMessage().isEmpty()) {
            Log.e(TAG, "*****ALERT - NO ERROR MESSAGE*******");
        } else {
            Log.e(TAG, idareError.getMessage(), exception);
        }
    }

    public IDAREErrorWrapper(String error, Exception exception) {
        this.exception = exception;

        // Ensure error message is mandatory for creating TRPErrorWrapper object
        if (error == null || error.isEmpty()) {
            Log.e(TAG, "*****ALERT - NO ERROR MESSAGE*******");
        } else {
            this.idareError = new IDAREError();
            idareError.setMessage(error);
            Log.e(TAG, idareError.getMessage(), exception);
        }
    }

    public IDAREError getIdareError() {
        return idareError;
    }

    public void setIdareError(IDAREError idareError) {
        this.idareError = idareError;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
