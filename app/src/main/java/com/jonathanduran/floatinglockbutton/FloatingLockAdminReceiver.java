package com.jonathanduran.floatinglockbutton;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by jonathanduran on 8/16/14.
 */
public final class FloatingLockAdminReceiver extends DeviceAdminReceiver {

    void showToast(Context context, CharSequence msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    void showToast(Context context, @StringRes int resourceId) {
        Toast.makeText(context, resourceId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, R.string.toast_enabled);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, R.string.toast_disabled);
    }
}
