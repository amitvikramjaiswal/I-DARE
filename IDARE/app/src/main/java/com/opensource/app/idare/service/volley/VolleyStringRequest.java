package com.opensource.app.idare.service.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * To get the string response from PRS server
 */
public class VolleyStringRequest extends StringRequest {

    private Map<String, String> mHeaders;
    private Map<String, String> mParams;
    private String mBody;

    public VolleyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        mHeaders = new HashMap<>();
        mParams = new HashMap<>();
    }

    public void addHeaders(String key, String value) {
        mHeaders.put(key, value);
    }

    public void addParams(String key, String value) {
        mParams.put(key, value);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        mHeaders = mHeaders != null ? mHeaders : super.getHeaders();
       // mHeaders.put("Cookie", Session.getInstance().getCookie());
        return mHeaders;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return mBody.getBytes();
    }

    public void setBody(String body) {
        this.mBody = body;
    }

}
