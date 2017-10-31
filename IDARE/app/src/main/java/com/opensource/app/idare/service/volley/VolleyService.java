package com.opensource.app.idare.service.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.opensource.app.idare.utils.Utility;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 * This is the Singleton Volley class that will be used to create a request queue and perform initialisations
 */
public class VolleyService {

    private static final String LOG_TAG = VolleyService.class.getSimpleName();
    private static VolleyService volleyUtils;
    private static Context context;
    private static TrustManager[] trustManagers;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private VolleyService(Context context) {
        VolleyService.context = context;
        requestQueue = getRequestQueue();
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static synchronized VolleyService getVolleyService(Context context) {
        if (volleyUtils == null) {
            volleyUtils = new VolleyService(context);
        }
        return volleyUtils;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {

            SSLContext sslContext;
            if (trustManagers == null) {
                //allowSSl();
            }

            try {
                sslContext = SSLContext.getInstance(Utility.TLS);
                sslContext.init(null, trustManagers, new SecureRandom());

                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
                        .getSocketFactory());
                HttpsURLConnection.setFollowRedirects(true);

                HttpStack httpStack = new HurlStack(null, sslContext
                        .getSocketFactory());
                requestQueue = Volley.newRequestQueue(VolleyService.context.getApplicationContext(), httpStack);
            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                Log.e(LOG_TAG, "Exception", e);
            }
        }
        return requestQueue;
    }

//    private static void allowSSl() {
//        if (Constants.APP_MODE_LOCALDEV.equals(Constants.getsAppMode()) || Constants.APP_MODE_QAMOCK.equals(Constants.getsAppMode())) {
//            //Enabled only with LOCALDEV(Mock Services) profile to allow self signed certificates
//            VolleyService.trustManagers = new TrustManager[]{new HttpsTrustManager()};
//            HttpsTrustManager.allowAllSSL();
//        }
//    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}
