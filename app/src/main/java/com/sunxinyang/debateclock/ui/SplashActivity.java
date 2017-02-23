package com.sunxinyang.debateclock.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.sunxinyang.debateclock.GamePrepareActivity;
import com.sunxinyang.debateclock.R;

/**
 * Created by sunxinyang on 2017/2/23.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int MSG_LOOP = 0;
    private static final int MSG_ENTER = 1;

    private static final int STAY_DURATION_SECONDS = 2;
    private int stayedDuration;

    private Handler eHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.splash_activity);

        eHandler = new MyHandler(this);
        eHandler.sendEmptyMessageDelayed(MSG_LOOP, 1000);
    }

    private void loop() {
        stayedDuration++;
        if (stayedDuration == STAY_DURATION_SECONDS) {
            eHandler.sendEmptyMessage(MSG_ENTER);
        } else {
            eHandler.sendEmptyMessageDelayed(MSG_LOOP, 1000);
        }
    }

    private void exit() {
        startActivity(new Intent(SplashActivity.this, GamePrepareActivity.class));
        finish();
    }

    private static class MyHandler extends Handler {
        SplashActivity activity;

        MyHandler(SplashActivity enterActivity) {
            activity = enterActivity;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOOP:
                    activity.loop();
                    break;
                case MSG_ENTER:
                    activity.exit();
                    break;
            }
        }
    }
}
