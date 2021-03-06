package com.opensource.app.idare.model.service.impl;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.google.gson.Gson;
import com.opensource.app.idare.application.IDareApp;
import com.opensource.app.idare.model.service.ServiceLocator;
import com.opensource.app.idare.model.service.URLs;
import com.opensource.app.idare.model.service.handler.IDAREResponseHandler;
import com.opensource.app.idare.model.service.volley.VolleyGSONGetRequest;
import com.opensource.app.idare.model.service.volley.VolleyGSONPostRequest;
import com.opensource.app.idare.model.service.volley.VolleyGSONPutRequest;
import com.opensource.app.idare.model.service.volley.VolleyService;
import com.opensource.app.idare.utils.AuthType;
import com.opensource.app.idare.utils.Constants;
import com.opensource.app.idare.utils.IDAREErrorWrapper;
import com.opensource.app.idare.utils.PreferencesManager;
import com.opensource.app.idare.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.opensource.app.idare.utils.Constants.GEOLOC;
import static com.opensource.app.idare.utils.Constants.LATITUDE;
import static com.opensource.app.idare.utils.Constants.LONGITUDE;
import static com.opensource.app.idare.utils.Constants.MAX_DISTANCE;
import static com.opensource.app.idare.utils.Constants.NEAR_SPHERE;
import static com.opensource.app.idare.utils.Constants.SEARCH_RADIUS_IN_MILES;

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
     * @param context           The android application conetext
     * @param username          The username of the user
     * @param userPass          The pword of user
     * @param url               The url of the servive to invoke
     * @param params            The request parameters
     * @param authType          The type of authentication required for the call
     * @param additionalHeaders The headers to be passed in the request
     * @param responseListener  The reference to the response handler
     * @param errorListener     The reference to the error handler
     */
    @Override
    public void login(Context context, String username, String userPass, URLs url, Map<String, Object> params, AuthType authType, Map<String, String> additionalHeaders, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        if (!Utils.isConnectedToInternet(context, errorListener)) {
            return;
        }
        Map<String, String> headers = getCommonHeaders(authType, context, username, userPass);
        if (additionalHeaders != null) {
            headers.putAll(additionalHeaders);
        }
        VolleyGSONGetRequest request = new VolleyGSONGetRequest(Request.Method.GET, buildUrl(url, params, null), url.getType(), headers, response -> {
            try {
                processResponse(response, responseListener, errorListener);
            } catch (Exception e) {
                Log.e(TAG, Constants.ERROR_OCCURRED_IN_SERVICE_CALL, e);
                errorListener.onError(new IDAREErrorWrapper(Constants.ERROR_OCCURRED_IN_SERVICE_CALL, e));
            }
        }, error -> errorListener.onError(new IDAREErrorWrapper(Constants.ERROR_OCCURRED_IN_SERVICE_CALL, error)));

        request.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d(TAG, Constants.SERVICE_CALL_STARTED);
        VolleyService.getVolleyService(IDareApp.getContext()).addToRequestQueue(request);
    }

    /**
     * This method has to be used for all Json GET request
     *
     * @param context           The android application conetext
     * @param url               The url of the servive to invoke
     * @param params            The request parameters
     * @param authType          The type of authentication required for the call
     * @param additionalHeaders The headers to be passed in the request
     * @param responseListener  The reference to the response handler
     * @param errorListener     The reference to the error handler
     */
    @Override
    public void executeGetRequest(Context context, URLs url, Map<String, Object> params, AuthType authType, Map<String, String> additionalHeaders, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        if (!Utils.isConnectedToInternet(context, errorListener)) {
            return;
        }
        Map<String, String> headers = null;
        if (url != URLs.URL_NEAR_BY_SEARCH_BASE_URL) {
            headers = getCommonHeaders(authType, context, null, null);
            if (additionalHeaders != null) {
                headers.putAll(additionalHeaders);
            }
        }
        final String strUrl = buildUrl(url, params, null);
        final VolleyGSONGetRequest request = new VolleyGSONGetRequest(Request.Method.GET, strUrl, url.getType(), headers, response -> {
            try {
                Log.d(TAG, "*** URL ***" + strUrl);
                processResponse(response, responseListener, errorListener);
            } catch (Exception e) {
                Log.e(TAG, Constants.ERROR_OCCURRED_IN_SERVICE_CALL, e);
                errorListener.onError(new IDAREErrorWrapper(Constants.ERROR_OCCURRED_IN_SERVICE_CALL, e));
            }
        }, error -> errorListener.onError(new IDAREErrorWrapper(Constants.ERROR_OCCURRED_IN_SERVICE_CALL, error)));

        request.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d(TAG, Constants.SERVICE_CALL_STARTED);
        VolleyService.getVolleyService(IDareApp.getContext()).addToRequestQueue(request);
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
    public void executePostRequest(Context context, URLs url, Map<String, Object> params, AuthType authType, Map<String, String> additionalHeaders, String body, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        if (!Utils.isConnectedToInternet(context, errorListener)) {
            return;
        }
        Log.d(TAG, "@@@ URL : " + url + " @@@");
        Log.d(TAG, "@@@ POST REQUEST @@@");
        Map<String, String> headers = getCommonHeaders(authType, context, null, null);
        if (additionalHeaders != null) {
            headers.putAll(additionalHeaders);
        }

        VolleyGSONPostRequest request = new VolleyGSONPostRequest(buildUrl(url, params, null), url.getType(), body, headers, response -> {
            try {
                Log.d(TAG, Constants.SUCCESS);
                Log.d(TAG, Constants.SERVICE_CALL_ENDED);
                processResponse(response, responseListener, errorListener);
            } catch (Exception e) {
                Log.e(TAG, Constants.ERROR_OCCURRED_IN_SERVICE_CALL, e);
                errorListener.onError(new IDAREErrorWrapper(Constants.ERROR_OCCURRED_IN_SERVICE_CALL, e));
            }
        }, error -> {
            Log.d(TAG, "@@@ onErrorResponse : " + error.getMessage() + " @@@");
            errorListener.onError(new IDAREErrorWrapper("Error Response ", error));
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d(TAG, Constants.SERVICE_CALL_STARTED);
        VolleyService.getVolleyService(IDareApp.getContext()).addToRequestQueue(request);
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
    public void executePutRequest(Context context, URLs url, Map<String, Object> params, AuthType authType, Map<String, String> additionalHeaders, String body, final IDAREResponseHandler.ResponseListener responseListener, final IDAREResponseHandler.ErrorListener errorListener) {
        if (!Utils.isConnectedToInternet(context, errorListener)) {
            return;
        }
        Log.d(TAG, "@@@ URL : " + url + " @@@");
        Log.d(TAG, "@@@ POST REQUEST @@@");
        Map<String, String> headers = getCommonHeaders(authType, context, null, null);
        if (additionalHeaders != null) {
            headers.putAll(additionalHeaders);
        }

        VolleyGSONPutRequest request = new VolleyGSONPutRequest(buildUrl(url, params, null), url.getType(), body, headers, response -> {
            try {
                Log.d(TAG, Constants.SUCCESS);
                Log.d(TAG, Constants.SERVICE_CALL_ENDED);
                processResponse(response, responseListener, errorListener);
            } catch (Exception e) {
                Log.e(TAG, Constants.ERROR_OCCURRED_IN_SERVICE_CALL, e);
                errorListener.onError(new IDAREErrorWrapper(Constants.ERROR_OCCURRED_IN_SERVICE_CALL, e));
            }
        }, error -> {
            Log.d(TAG, "@@@ onErrorResponse : " + error.getMessage() + " @@@");
            errorListener.onError(new IDAREErrorWrapper("Error Response ", error));
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                Constants.MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.d(TAG, Constants.SERVICE_CALL_STARTED);
        VolleyService.getVolleyService(IDareApp.getContext()).addToRequestQueue(request);
    }


    protected String buildUrl(URLs urLs, Map<String, Object> queryParam, Map<String, String> modifier) {
        String url = urLs.fullURL();
        switch (urLs) {
            /** FALLTHROUGH CASES **/
            case URL_CREATE_ACCOUNT:
                break;
            case URL_UPDATE_PROFILE:
                String id = (String) queryParam.get(Constants.ID);
                url += Constants.SEPARATOR + id;
                break;
            case URL_TRIGGER_NOTIFICATION:
                break;
            case URL_IS_USER_EXISTS:
                String keyUsername = queryParam.keySet().iterator().next();
                String valueUserName = (String) queryParam.get(keyUsername);
                url += String.format(Constants.QUERY + "{\"%s\":\"%s\"}", keyUsername, valueUserName);
                break;
            case URL_IS_PASSWORD_EXISTS:
            case URL_FETCH_USERS:
            case URL_FETCH_NOTIFICATIONS:
                String key = queryParam.keySet().iterator().next();
                String value = (String) queryParam.get(key);
                url += String.format(Constants.QUERY + "{\"%s\":\"%s\"}", key, value);
                break;
            case URL_FETCH_NEARBY_USERS:
                double lat = (double) queryParam.get(LATITUDE);
                double lng = (double) queryParam.get(LONGITUDE);
                url += String.format(Constants.QUERY + "{\"%s\":{\"%s\":[%f,%f],\"%s\":\"%s\"}}", GEOLOC, NEAR_SPHERE, lng, lat, MAX_DISTANCE, SEARCH_RADIUS_IN_MILES);
                break;
            case URL_NEAR_BY_SEARCH_BASE_URL:
                Set<String> keys = queryParam.keySet();
                String query = "";
                for (String keyParam : keys) {
                    query += keyParam + "=" + queryParam.get(keyParam) + "&";
                }
                query.substring(0, query.lastIndexOf('&'));
                url += "?" + query;
                break;
            default:
                if (urLs.getRequestParam() != null) {
                    url += urLs.getRequestParam();
                }
                break;
        }

        Log.i(TAG, "Module: " + urLs + ". URL being called: " + url);
        return url;
    }

    public Map<String, String> getCommonHeaders(AuthType authType, Context context, String username, String userPass) {
        // Add Required Headers
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
        // add headers <key,value>
        String credentials;
        switch (authType) {
            case USER_CREDENTIALS:
                if (PreferencesManager.getInstance(context).wasUserLoggedIn()) {
                    credentials = PreferencesManager.getInstance(context).getUsername() + ":" +
                            PreferencesManager.getInstance(context).getUserPass();
                } else {
                    credentials = username + ":" + userPass;
                }
                break;
            case MASTER_SECRET:
                credentials = Constants.APP_KEY + ":" + Constants.MASTER_SECRET;
                break;
            case APP_CREDENTIALS:
            default:
                credentials = Constants.APP_KEY + ":" + Constants.APP_SECRET;
        }
        String auth = Constants.BASIC
                + Base64.encodeToString(credentials.getBytes(),
                Base64.NO_WRAP);
        headers.put(Constants.AUTHORISATION, auth);
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
