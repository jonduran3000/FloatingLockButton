package com.jonathanduran.floatinglockbutton;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {
    static final int RESULT_ENABLE = 1;
    public static final String PREF = "floating_lock_button_pref";
    public static final String ADMIN_ENABLED = "admin_enabled";
    public static final String BUTTON_DISPLAYED = "button_displayed";

    private ComponentName componentName;
    private ToggleButton enableAdmin;
    private ToggleButton displaySwitch;
    private TextView textView;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Util.loadTypefaces(this);
        styleActionBar();

        preferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);

        textView = (TextView)findViewById(R.id.textView);
        textView.setTypeface(Util.getRegular());

        TextView adminEnabledLabel = (TextView)findViewById(R.id.admin_enabled_label);
        adminEnabledLabel.setTypeface(Util.getRegular());
        TextView displayLockLabel = (TextView)findViewById(R.id.display_lock_label);
        displayLockLabel.setTypeface(Util.getRegular());

        componentName = new ComponentName(this, Admin.class);

        enableAdmin = (ToggleButton)findViewById(R.id.admin_enabled);
        enableAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    startEnableAdminIntent();
                else {
                    if (displaySwitch != null && displaySwitch.isChecked()) {
                        displaySwitch.setChecked(false);
                        stopService(new Intent(MainActivity.this, FloatingButtonService.class));
                        preferences.edit().putBoolean(BUTTON_DISPLAYED, false).commit();
                    }
                    preferences.edit().putBoolean(ADMIN_ENABLED, false).commit();
                    textView.setText(R.string.initial_message);
                }
            }
        });

        displaySwitch = (ToggleButton)findViewById(R.id.display_lock);
        displaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(enableAdmin != null && enableAdmin.isChecked()) {
                        startService(new Intent(MainActivity.this, FloatingButtonService.class));
                        textView.setText(R.string.button_displayed);
                        preferences.edit().putBoolean(BUTTON_DISPLAYED, true).commit();
                    }
                } else {
                    stopService(new Intent(MainActivity.this, FloatingButtonService.class));
                    preferences.edit().putBoolean(BUTTON_DISPLAYED, false).commit();
                    textView.setText(R.string.admin_enabled);
                }
            }
        });
    }

    private void styleActionBar() {
        SpannableString title = new SpannableString(getString(R.string.app_name));
        title.setSpan(new TypefaceSpan(this, "RobotoCondensed-Bold.ttf"), 0, title.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onResume() {
        super.onResume();

        boolean adminEnabled = preferences.getBoolean(ADMIN_ENABLED, false);
        boolean buttonDisplayed = preferences.getBoolean(BUTTON_DISPLAYED, false);

        if(adminEnabled & buttonDisplayed) {
            enableAdmin.setChecked(true);
            displaySwitch.setChecked(true);
            textView.setText(R.string.button_displayed);
        } else if(adminEnabled & !buttonDisplayed) {
            enableAdmin.setChecked(true);
            displaySwitch.setChecked(false);
            textView.setText(R.string.admin_enabled);
        } else {
            enableAdmin.setChecked(false);
            displaySwitch.setChecked(false);
            textView.setText(R.string.initial_message);
        }
    }

    private void startEnableAdminIntent() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Floating Lock Button needs to activate the Administrator in order to lock the screen.");
        startActivityForResult(intent, RESULT_ENABLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_ENABLE:
                if (resultCode == Activity.RESULT_OK) {
                    Log.i("MainActivity", "Admin enabled!");
                    textView.setText(R.string.admin_enabled);
                    preferences.edit().putBoolean(ADMIN_ENABLED, true).commit();
                } else {
                    Log.i("MainActivity", "Admin enable FAILED!");
                    textView.setText(R.string.error_message);
                }
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
