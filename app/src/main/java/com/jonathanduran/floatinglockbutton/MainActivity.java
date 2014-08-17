package com.jonathanduran.floatinglockbutton;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;


public class MainActivity extends Activity {

    static final int RESULT_ENABLE = 1;
    private ComponentName componentName;
    private Switch adminEnabled;
    private Switch displaySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        componentName = new ComponentName(this, Admin.class);

        adminEnabled = (Switch)findViewById(R.id.admin_enabled);
        adminEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    startEnableAdminIntent();
                else {
                    if(displaySwitch != null && displaySwitch.isChecked()) {
                        displaySwitch.setChecked(false);
                        stopService(new Intent(MainActivity.this, FloatingButtonService.class));
                    }
                }
            }
        });

        displaySwitch = (Switch)findViewById(R.id.display_lock);
        displaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if(adminEnabled != null && adminEnabled.isChecked()) {
                        startService(new Intent(MainActivity.this, FloatingButtonService.class));
                    }
                } else
                    stopService(new Intent(MainActivity.this, FloatingButtonService.class));
            }
        });
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
                if (resultCode == Activity.RESULT_OK)
                    Log.i("MainActivity", "Admin enabled!");
                else
                    Log.i("MainActivity", "Admin enable FAILED!");
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
