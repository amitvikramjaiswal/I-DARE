package com.opensource.app.idare.pojo.common;

import com.google.gson.annotations.SerializedName;

/**
 * Created by akokala on 10/31/2017.
 */

public class IDAREError {

    @SerializedName("message")
    private String message;
    @SerializedName("errorCode")
    private String errorCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
