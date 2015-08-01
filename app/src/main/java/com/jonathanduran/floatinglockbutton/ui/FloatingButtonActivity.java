package com.jonathanduran.floatinglockbutton.ui;


import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.jonathanduran.floatinglockbutton.FloatingLockAdminReceiver;
import com.jonathanduran.floatinglockbutton.R;
import com.jonathanduran.floatinglockbutton.ui.misc.AppCompatPreferenceActivity;
import com.jonathanduran.floatinglockbutton.utils.LogUtils;

/**
 * Created by jonathanduran on 7/26/15.
 */
public final class FloatingButtonActivity extends AppCompatPreferenceActivity {
    private static final int RESULT_ENABLE = 1;
    private static final String TAG = LogUtils.makeLogTag(FloatingButtonActivity.class);

    private ComponentName componentName;
    private String prompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_button);
        componentName = new ComponentName(this, FloatingLockAdminReceiver.class);
    }

    private String getPermissionPrompt() {
        if (prompt == null) {
            prompt = getResources().getString(R.string.permission_prompt);
        }
        return prompt;
    }

    public void startEnableAdminIntent() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getPermissionPrompt());
        startActivityForResult(intent, RESULT_ENABLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE: {
                if (resultCode == Activity.RESULT_OK) {
                    LogUtils.i(TAG, "Admin enabled!");
                } else {
                    LogUtils.i(TAG, "Admin enable FAILED! Result code = " + resultCode);
                }
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
