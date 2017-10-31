package com.opensource.app.idare.service.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.Utility;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Handles Http Post calls
 */
public class VolleyGSONPostRequest<T> extends JsonRequest<T> {

    private static final String TAG = VolleyGSONPostRequest.class.getSimpleName();
    private final Gson gson;
    private final Type type;
    private final Listener<T> listener;
    private Map<String, String> mHeaders;
    private String body = null;


    public VolleyGSONPostRequest(String url, Type type, String body, Map<String, String> mHeaders,
                                 Listener<T> listener, Response.ErrorListener errorListener) {

        super(Method.POST, url, body, listener, errorListener);
        this.type = type;
        gson = new Gson();
        this.listener = listener;
        this.mHeaders = mHeaders;
        this.body = body;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        mHeaders = mHeaders != null ? mHeaders : super.getHeaders();
        //mHeaders.put(Constants.COOKIE, Session.getInstance().getCookie());
        mHeaders.put(Utility.CONTENT_TYPE, Utility.APPLICATION_JSON);
        return mHeaders;
    }

    @Override
    public byte[] getBody() {
        return body.getBytes();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return (Response<T>) Response.success(gson.fromJson(json, type), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        } catch (JsonParseException e) {
            try {
                Log.e(TAG, "exception", e);
                String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                return (Response<T>) Response.success(gson.fromJson(json, IDAREErrorWrapper.class), HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e1) {
                Log.e(TAG, "exception", e1);
                return Response.error(new ParseError(e1));
            }
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
