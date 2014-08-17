package com.jonathanduran.floatinglockbutton;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * Created by jonathanduran on 8/16/14.
 */
public class FloatingButtonService extends Service {

    WindowManager windowManager;
    ImageButton floatingButton;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);

        floatingButton = new ImageButton(this);
        floatingButton.setLayoutParams(new ViewGroup.LayoutParams(48, 48));
        floatingButton.setBackgroundResource(R.drawable.circle_button);
        floatingButton.setImageResource(R.drawable.ic_action_android);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                150, 150, WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.RIGHT;
        params.x = 0;
        params.y = 100;

        windowManager.addView(floatingButton, params);

        floatingButton.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            private boolean moved = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        moved = false;
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if(!moved)
                            floatingButton.performClick();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        moved = true;
                        params.x = initialX - (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(floatingButton, params);
                        return true;
                }
                return false;
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lockScreen();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(floatingButton != null)
            windowManager.removeView(floatingButton);
    }

    private void lockScreen() {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager)getSystemService(DEVICE_POLICY_SERVICE);
        devicePolicyManager.lockNow();
    }
}
