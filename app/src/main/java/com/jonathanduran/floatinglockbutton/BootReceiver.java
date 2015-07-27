package com.jonathanduran.floatinglockbutton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.jonathanduran.floatinglockbutton.ui.MainActivity;

/**
 * Created by jonathanduran on 8/17/14.
 */
public final class BootReceiver extends BroadcastReceiver {
    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        preferences = context.getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);

        boolean serviceEnabled = preferences.getBoolean(MainActivity.BUTTON_DISPLAYED, false);

        if (serviceEnabled) {
            Intent floatingService = new Intent(context, FloatingButtonService.class);
            context.startService(floatingService);
        }
    }
}
