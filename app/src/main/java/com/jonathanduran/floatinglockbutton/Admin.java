package com.jonathanduran.floatinglockbutton;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by jonathanduran on 8/16/14.
 */
public class Admin extends DeviceAdminReceiver {

    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "Floating Lock Button: enabled");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "Floating Lock Button: disabled");
    }
}
