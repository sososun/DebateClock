package com.sunxinyang.debateclock.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sunxinyang.debateclock.R;
import com.sunxinyang.debateclock.util.CommonUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by soso-sun on 2017/2/20.
 */

public class GamingActivity extends AppCompatActivity implements View.OnClickListener {

    TextView positive, positiveTitle, positiveTime, negative, negativeTitle, negativeTime;
    Button nextButton, settingButton, switchButton;
    String positiveTimeString, negativeTimeString;
    private Timer timer = null;//计时器
    private TimerTask timerTask = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_clock_activity);
        init();
        buttonClick();
        loadData();
    }

    private void loadData() {
        SharedPreferences preferences = getSharedPreferences("game_title", Context.MODE_PRIVATE);
        int step = getIntent().getIntExtra(CommonUtils.LIST_NUM, 0);
        positive.setText(preferences.getString("positiveName", null));
        negative.setText(preferences.getString("negativeName", null));
        positiveTitle.setText(preferences.getString("positiveTitle", null));
        negativeTitle.setText(preferences.getString("negativeTitle", null));
        if (CommonUtils.ruleList.get(step).timeModel == CommonUtils.BOTH) {
            positiveTime.setVisibility(View.VISIBLE);
            negativeTime.setVisibility(View.VISIBLE);
            positiveTimeString = CommonUtils.ruleList.get(step).positiveTime;
            negativeTimeString = CommonUtils.ruleList.get(step).negativeTime;
            positiveTime.setText(positiveTimeString + "秒");
            negativeTime.setText(negativeTimeString + "秒");

        } else if (CommonUtils.ruleList.get(step).timeModel == CommonUtils.POSITIVE) {
            positiveTime.setVisibility(View.VISIBLE);
            negativeTime.setVisibility(View.GONE);
            positiveTime.setText(CommonUtils.ruleList.get(step).positiveTime + "秒");
        } else if (CommonUtils.ruleList.get(step).timeModel == CommonUtils.NEGATIVE) {
            positiveTime.setVisibility(View.GONE);
            negativeTime.setVisibility(View.VISIBLE);
            negativeTime.setText(CommonUtils.ruleList.get(step).negativeTime + "秒");
        }
    }

    private void init() {
        positive = (TextView) findViewById(R.id.positive);
        negative = (TextView) findViewById(R.id.negative);
        positiveTitle = (TextView) findViewById(R.id.positiveTitle);
        negativeTitle = (TextView) findViewById(R.id.negativeTitle);
        positiveTime = (TextView) findViewById(R.id.positiveTime);
        negativeTime = (TextView) findViewById(R.id.negativeTime);
        nextButton = (Button) findViewById(R.id.nextButton);
        settingButton = (Button) findViewById(R.id.settingButton);
        switchButton = (Button) findViewById(R.id.switchButton);
    }

    private void buttonClick() {
        nextButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        switchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextButton:
                break;
            case R.id.settingButton:
                startTime();
                break;
            case R.id.switchButton:
                break;
            default:
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            positiveTime.setText(msg.arg1 + "");
            startTime();
        }

        ;
    };

    /**
     * 开始自动减时
     */
    private void startTime() {
        if (timer == null) {
            timer = new Timer();
        }

        timerTask = new TimerTask() {

            @Override
            public void run() {
                int i = 90;
                i--;//自动减1
                Message message = Message.obtain();
                message.arg1 = i;
                mHandler.sendMessage(message);//发送消息
            }
        };
        timer.schedule(timerTask, 1000);//1000ms执行一次
    }

    /**
     * 停止自动减时
     */
    private void stopTime() {
        if (timer != null)
            timer.cancel();

    }
}
