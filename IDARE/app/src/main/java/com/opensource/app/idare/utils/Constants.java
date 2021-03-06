package com.opensource.app.idare.utils;

import com.opensource.app.idare.component.service.IDareLocationService;

/**
 * Created by ajaiswal on 5/27/2016.
 */
public class Constants {

    // Kinvey Dev Environment
    public static final String SERVICE_URL = "https://baas.kinvey.com";
    public static final String APP_KEY = "kid_HykFqQjCZ";
    public static final String APP_SECRET = "41968afb09a2461ab405fc48e751ac41";
    public static final String MASTER_SECRET = "f06a916f1f3e419096d69e265f694e6d";
    public static final String APP_DATA = "appdata";
    public static final String FWD_SLASH = "/";

    public static final String NEAR_BY_SEARCH_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String TLS = "TLS";
    public static final String ERROR_OCCURRED_IN_SERVICE_CALL = "Error Occurred in the service call";
    public static final long SPLASH_TIME_OUT = 2000;
    public static final String OTP_VALIDATION = "123456";
    public static final int MY_SOCKET_TIMEOUT_MS = 60000;
    public static final String SERVICE_CALL_STARTED = "SERVICE CALL STARTED:";
    public static final String NO_NETWORK_ERROR_CODE = "N0";
    public static final String ERROR_RESPONSE_MSG = "Error response ";
    public static final String SUCCESS = "SUCCESS";
    public static final String SERVICE_CALL_ENDED = "SERVICE CALL ENDED:";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String AUTHORISATION = "Authorization";
    public static final String KEY_USER_CONTEXT = "KEY_USER_CONTEXT";
    public static final String IS_ACTIVE = "IS_ACTIVE";
    public static final String KEY_NOT_FIRST_LAUNCH = "KEY_NOT_FIRST_LAUNCH";
    public static final String USER_NAME = "USER_NAME";
    public static final String USERNAME = "username";
    public static final String QUERY = "?query=";
    public static final String PASSWORD = "password";
    public static final String ANDROID = "android";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String ID = "_id";
    public static final String SEPARATOR = "/";
    public static final String ARRAY_RES_ID = "ARRAY_RES_ID";
    public static final String ACTION_NOTIFICATION = "com.opensource.app.idare.NEW_NOTIFICATION";
    public static final String ACTION_NOTIFICATION_DISMISS = "com.opensource.app.idare.NOTIFICATION_DISMISSED";
    public static final String GEOLOC = "_geoloc";
    public static final String NEAR_SPHERE = "$nearSphere";
    public static final String MAX_DISTANCE = "$maxDistance";
    public static final double SEARCH_RADIUS_IN_KM = 8.23;
    public static final String DATE_PATTERN = "h:mm a";
    private static final double KM_IN_MILE = 0.621371;
    public static final double SEARCH_RADIUS_IN_MILES = SEARCH_RADIUS_IN_KM * KM_IN_MILE;
    public static final String SEARCH_RADIUS = "3000";
    public static final int MINIMUM_CLUSTER_SIZE = 10;
    public static String BASIC = "Basic ";




    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 30000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 15000;
    public static final float SMALLEST_DISPLACEMENT = 50.0f;
    public static final String ACTION_LOCATION_BROADCAST = IDareLocationService.class.getName() + "LocationBroadcast";
    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
}
