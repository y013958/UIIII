package com.study.android.mytry.certification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import net.daum.mf.map.api.MapView;

public class AlarmReceiver extends BroadcastReceiver {
int i = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("certificate", i+"분 경과");
        i++;
    }
}
