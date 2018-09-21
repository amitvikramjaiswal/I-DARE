package com.opensource.app.idare.component.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.opensource.app.idare.utils.ShakeEventManager;
import com.opensource.app.idare.utils.Utils;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class FakeCallService extends Service implements ShakeEventManager.ShakeListener {


    private static final String TAG = FakeCallService.class.getSimpleName();
    private static boolean isRunning;
    private ServiceHandler serviceHandler;
    private ShakeEventManager shakeEventManager;
    private Looper serviceLooper;

    public static boolean isIsRunning() {
        return isRunning;
    }

    @Override
    public void onCreate() {
        // Start up the thread running the service. Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block. We also make it
        // background priority so CPU-intensive work doesn't disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments", THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onShake() {
        Log.d(TAG, TAG + " onShake()");
        Utils.fakeCall(this);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(TAG, TAG + " started. onStartCommand()");
        isRunning = true;

        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, TAG + " destroyed. onDestroy()");
        isRunning = false;
        shakeEventManager.deregister();
        super.onDestroy();
    }

    // Handler that receives messages from the thread
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            shakeEventManager = new ShakeEventManager();
            shakeEventManager.setListener(FakeCallService.this);
            shakeEventManager.init(FakeCallService.this);
            shakeEventManager.register();
        }
    }
}
