package com.opensource.app.idare.model.service.impl;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.opensource.app.idare.application.IDareApp;
import com.opensource.app.idare.model.service.ServiceLocator;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.volley.VolleyGSONGetRequest;
import com.opensource.app.idare.model.service.volley.VolleyGSONPostRequest;
import com.opensource.app.idare.model.service.volley.VolleyService;
import com.opensource.app.idare.model.service.volley.VolleyStringRequest;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.model.service.URLs;
import com.opensource.app.idare.utils.Utility;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocatorImpl implements ServiceLocator {

    private static final String TAG = ServiceLocatorImpl.class.getSimpleName();
    private static ServiceLocatorImpl serviceLocator;
    private Gson gson;

    private ServiceLocatorImpl() {
        gson = new Gson();
    }

    public static ServiceLocatorImpl getInstance() {
        if (serviceLocator == null) {
            serviceLocator = new ServiceLocatorImpl();
        }
        return serviceLocator;
    }

    /**
     * This method has to be used for all Json GET request
     *
     * @param context          The android application conetext
     * @param url              The url of the servive to invoke
     * @param params           The request parameters
     * @param responseListener The reference to the response handler
     * @param errorListener    The reference to the error handler
     * @param headers
     */
    @Override
    public void executeGetRequest(Context context, URLs url, Map<String, String> params, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener, Map<String, String> headers) {
        executeGetRequest(context, url, params, headers, responseListener, errorListener);
    }


    /**
     * This method has to be used for all Json GET request
     *
     * @param context          The android application conetext
     * @param url              The url of the servive to invoke
     * @param params           The request parameters
     * @param headers          The headers to be passed in the request
     * @param responseListener The reference to the response handler
     * @param errorListener    The reference to the error handler
     */
    @Override
    public void executeGetRequest(Context context, URLs url, Map<String, String> params, Map<String, String> headers, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        if (!IDareApp.isConnectedToInternet(errorListener)) {
            return;
        }
        if (headers != null) {
            headers.putAll(getCommonHeaders());
        }
        VolleyGSONGetRequest request = new VolleyGSONGetRequest(Request.Method.GET, buildUrl(url, params), url.getType(), headers, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                try {
                    processResponse(response, responseListener, errorListener);
                } catch (Exception e) {
                    Log.e(TAG, Utility.ERROR_OCCURRED_IN_SERVICE_CALL, e);
                    errorListener.onError(new IDAREErrorWrapper(Utility.ERROR_OCCURRED_IN_SERVICE_CALL, e));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onError(new IDAREErrorWrapper(Utility.ERROR_OCCURRED_IN_SERVICE_CALL, error));
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                Utility.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d(TAG, Utility.SERVICE_CALL_STARTED);
        VolleyService.getVolleyService(IDareApp.getContext()).addToRequestQueue(request);
    }


    /**
     * This method has to be used for all String GET request
     *
     * @param context
     * @param url
     * @param responseListener
     * @param errorListener
     */
    @Override
    public void executeGetStringRequest(Context context, URLs url, Map<String, String> params, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        if (!IDareApp.isConnectedToInternet(errorListener)) {
            return;
        }
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.GET, buildUrl(url, params), new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                try {
                    processResponse(response, responseListener, errorListener);
                } catch (Exception e) {
                    Log.e(TAG, Utility.ERROR_OCCURRED_IN_SERVICE_CALL, e);
                    errorListener.onError(new IDAREErrorWrapper(Utility.ERROR_OCCURRED_IN_SERVICE_CALL, e));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorListener.onError(new IDAREErrorWrapper(Utility.ERROR_RESPONSE_MSG, error));
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                Utility.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d(TAG, Utility.SERVICE_CALL_STARTED);
        VolleyService.getVolleyService(IDareApp.getContext()).addToRequestQueue(request);
    }

    /**
     * This method has to be used for all Json POST request
     *
     * @param context          The application context
     * @param url              The service url
     * @param params           The request parameters
     * @param body             The request payload
     * @param responseListener The reference to the response handler
     * @param errorListener    The refernece to the error handler
     */
    @Override
    public void executePostRequest(Context context, URLs url, Map<String, String> params, String body, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        executePostRequest(context, url, params, null, body, responseListener, errorListener);
    }

    /**
     * This method has to be used for all Json POST request
     *
     * @param context           The application context
     * @param url               The service url
     * @param params            The request parameters
     * @param additionalHeaders The additional headers to be passed in the request
     * @param body              The request payload
     * @param responseListener  The reference to the response handler
     * @param errorListener     The refernece to the error handler
     */
    @Override
    public void executePostRequest(Context context, URLs url, Map<String, String> params, Map<String, String> additionalHeaders, String body, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        if (!IDareApp.isConnectedToInternet(errorListener)) {
            return;
        }
        Log.d(TAG, "@@@ URL : " + url + " @@@");
        Log.d(TAG, "@@@ POST REQUEST @@@");
        Map<String, String> headers = getCommonHeaders();
        if (additionalHeaders != null) {
            headers.putAll(additionalHeaders);
        }

        VolleyGSONPostRequest request = new VolleyGSONPostRequest(buildUrl(url, params), url.getType(), body, headers, new Response.Listener() {

            @Override
            public void onResponse(Object response) {
                try {
                    Log.d(TAG, Utility.SUCCESS);
                    Log.d(TAG, Utility.SERVICE_CALL_ENDED);
                    processResponse(response, responseListener, errorListener);
                } catch (Exception e) {
                    Log.e(TAG, Utility.ERROR_OCCURRED_IN_SERVICE_CALL, e);
                    errorListener.onError(new IDAREErrorWrapper(Utility.ERROR_OCCURRED_IN_SERVICE_CALL, e));
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "@@@ onErrorResponse : " + error.getMessage() + " @@@");
                errorListener.onError(new IDAREErrorWrapper("Error Response ", error));
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                Utility.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d(TAG, Utility.SERVICE_CALL_STARTED);
        VolleyService.getVolleyService(IDareApp.getContext()).addToRequestQueue(request);
    }

    protected String buildUrl(URLs urLs, Map<String, String> params) {
        String url = urLs.fullURL();
        Uri uri = Uri.parse(url);
        switch (urLs) {
            /*** FALLTHROUGH CASES **/
            /*case URL_LOGIN:
                if (params != null && !params.isEmpty()) {
                    url = uri.buildUpon().appendQueryParameter(Constants.USER_NAME, params.get(Constants.USER_NAME)).appendQueryParameter(Constants.PASSWORD, params.get(Constants.PASSWORD)).build().toString();
                }
                break;*/
            default:
                if (urLs.getRequestParam() != null) {
                    url += urLs.getRequestParam();
                }
                break;
        }

        Log.i(TAG, "Module: " + urLs + ". URL being called: " + url);
        return url;
    }

    private Map<String, String> getCommonHeaders() {
        // Add Required Headers
        Map<String, String> headers = new HashMap<>();
        headers.put(Utility.CONTENT_TYPE, Utility.APPLICATION_JSON);
        return headers;
    }

    private void processResponse(Object response, IDAREResponseHandler.ResponseListener responseListener, IDAREResponseHandler.ErrorListener errorListener) {
        if (response instanceof IDAREErrorWrapper) {
            errorListener.onError((IDAREErrorWrapper) response);
        } else {
            responseListener.onSuccess(response);
        }
    }

}
