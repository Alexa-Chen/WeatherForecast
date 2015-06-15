package com.weatherforecast.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by chenmo on 2015/6/15.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i  = new Intent(context,AutoUpdateReceiver.class);
        context.startService(i);
    }
}
