package com.opensource.app.idare.service.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.opensource.app.idare.utils.IDAREErrorWrapper;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles Http Get calls
 */
public class VolleyGSONGetRequest<T> extends Request<T> {

    private static final String TAG = VolleyGSONGetRequest.class.getSimpleName();
    private final Gson gson;
    private final Type type;
    private final Listener<T> listener;
    private Map<String, String> mHeaders;


    public VolleyGSONGetRequest(int method, String url, Type type,
                                Listener<T> listener, Response.ErrorListener errorListener) {

        super(method, url, errorListener);
        this.type = type;
        gson = new Gson();
        this.listener = listener;
        mHeaders = new HashMap<>();

    }

    public VolleyGSONGetRequest(int method, String url, Type type, Map<String, String> headers,
                                Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, url, type, listener, errorListener);
        if (headers != null) {
            mHeaders.putAll(headers);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        mHeaders = mHeaders != null ? mHeaders : super.getHeaders();
//        mHeaders.put("Cookie", Session.getInstance().getCookie());
        return mHeaders;
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
