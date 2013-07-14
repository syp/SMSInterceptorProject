package com.example.smsinterceptor;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by stephen on 13-7-14.
 */
public class InterceptorService extends Service {

    private static final String SMS_ACTION="android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SMS_ACTION);
        filter.setPriority(Integer.MAX_VALUE);
        registerReceiver(new SMSReceiver(), filter);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public class SMSReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(SMS_ACTION.equals(intent.getAction())){
                StringBuffer smsAddress = new StringBuffer();
                StringBuffer smsContent = new StringBuffer();
                Bundle bundle = intent.getExtras();
                if(bundle != null){
                    Object[] pdusObjects = (Object [])bundle.get("pdus");
                    SmsMessage[] messages = new SmsMessage[pdusObjects.length];
                    for(int i=0; i<pdusObjects.length; i++){
                        messages[i] = SmsMessage.createFromPdu((byte[])pdusObjects[i]);
                    }
                    for(SmsMessage message:messages){
                        smsAddress.append(message.getDisplayOriginatingAddress());
                        smsContent.append(message.getDisplayMessageBody());
                        Log.d("SMSInterceptor", "SMS address: "+smsAddress+", SMS content:"+smsContent);
                    }
                }
            }

        }
    }
}
