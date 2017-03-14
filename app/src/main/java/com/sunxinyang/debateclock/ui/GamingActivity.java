package com.sunxinyang.debateclock.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sunxinyang.debateclock.R;
import com.sunxinyang.debateclock.util.CommonUtils;

/**
 * Created by soso-sun on 2017/2/20.
 */

public class GamingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView positive, positiveTitle, positiveTime, negative, negativeTitle, negativeTime,showTime,stageName;
    private Button previousButton, nextButton, settingButton, switchButton;
    private int positiveTimeInt, negativeTimeInt, step;
    private int timeModel,bothTag = CommonUtils.POSITIVE;
    private TimeCount positiveTimeCount, negativeTimeCount;
    private boolean remindTime = false;
    private boolean isExit = false;
    private static final int EXIT_TIME = 80;

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
        step = getIntent().getIntExtra(CommonUtils.LIST_NUM, 0);
        timeModel = CommonUtils.ruleList.get(step).timeModel;
        remindTime = CommonUtils.ruleList.get(step).tips;
        positive.setText(preferences.getString("positiveName", null));
        negative.setText(preferences.getString("negativeName", null));
        positiveTitle.setText(preferences.getString("positiveTitle", null));
        negativeTitle.setText(preferences.getString("negativeTitle", null));
        stageName.setText(CommonUtils.ruleList.get(step).stageName);
        settingButton.setText(R.string.start_time);
        if (timeModel == CommonUtils.BOTH) {
            positiveTime.setVisibility(View.VISIBLE);
            negativeTime.setVisibility(View.VISIBLE);
            positiveTimeInt = CommonUtils.ruleList.get(step).positiveTime;
            negativeTimeInt = CommonUtils.ruleList.get(step).negativeTime;
            positiveTime.setText(positiveTimeInt + "秒");
            negativeTime.setText(negativeTimeInt + "秒");
            switchButton.setVisibility(View.VISIBLE);
            switchButtonClickState(false);
        } else if (timeModel == CommonUtils.POSITIVE) {
            positiveTime.setVisibility(View.VISIBLE);
            negativeTime.setVisibility(View.GONE);
            positiveTimeInt = CommonUtils.ruleList.get(step).positiveTime;
            positiveTime.setText(CommonUtils.ruleList.get(step).positiveTime + "秒");
            switchButton.setVisibility(View.GONE);
        } else if (timeModel == CommonUtils.NEGATIVE) {
            positiveTime.setVisibility(View.GONE);
            negativeTime.setVisibility(View.VISIBLE);
            negativeTimeInt = CommonUtils.ruleList.get(step).negativeTime;
            negativeTime.setText(CommonUtils.ruleList.get(step).negativeTime + "秒");
            switchButton.setVisibility(View.GONE);
        }
        if(step == CommonUtils.ruleList.size() - 1){//当是最后一个环节的时候，下一环节按钮变为完成比赛
            nextButton.setText(R.string.finish_game);
        }else if (step == 0){
            previousButton.setVisibility(View.GONE);
        }
    }

    private void init() {
        positive = (TextView) findViewById(R.id.positive);
        negative = (TextView) findViewById(R.id.negative);
        positiveTitle = (TextView) findViewById(R.id.positiveTitle);
        negativeTitle = (TextView) findViewById(R.id.negativeTitle);
        positiveTime = (TextView) findViewById(R.id.positiveTime);
        negativeTime = (TextView) findViewById(R.id.negativeTime);
        showTime = (TextView) findViewById(R.id.showTime);
        stageName = (TextView) findViewById(R.id.stageName);
        previousButton = (Button) findViewById(R.id.previousButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        settingButton = (Button) findViewById(R.id.settingButton);
        switchButton = (Button) findViewById(R.id.switchButton);
    }

    private void buttonClick() {
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        switchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switchButton:
                if(bothTag == CommonUtils.POSITIVE){
                    if(positiveTimeCount != null){
                        positiveTimeCount.cancel();
                        bothTag = CommonUtils.NEGATIVE;
                        startNegativeTime();
                    }
                }else if (bothTag == CommonUtils.NEGATIVE){
                    if (negativeTimeCount != null){
                        negativeTimeCount.cancel();
                        bothTag = CommonUtils.POSITIVE;
                        startPositiveTime();
                    }
                }
                break;
            case R.id.settingButton:
                if(settingButton.getText().equals(getText(R.string.start_time))){
                    if(timeModel == CommonUtils.BOTH){
                        if(bothTag == CommonUtils.POSITIVE){
                            startPositiveTime();
                        }else if (bothTag == CommonUtils.NEGATIVE){
                            startNegativeTime();
                        }
                        switchButtonClickState(true);
                    }else if (timeModel == CommonUtils.POSITIVE){
                        startPositiveTime();
                    }else if (timeModel == CommonUtils.NEGATIVE){
                        startNegativeTime();
                    }
                    settingButton.setText(R.string.stop_time);
                }else if (settingButton.getText().equals(getText(R.string.stop_time))){
                    if(timeModel == CommonUtils.POSITIVE){
                        positiveTimeCount.cancel();
                    }else if (timeModel == CommonUtils.NEGATIVE){
                        negativeTimeCount.cancel();
                    }else if (timeModel == CommonUtils.BOTH){
                        if(bothTag == CommonUtils.POSITIVE){
                            positiveTimeCount.cancel();
                        }else if (bothTag == CommonUtils.NEGATIVE){
                            negativeTimeCount.cancel();
                        }
                        switchButtonClickState(false);
                    }
                    settingButton.setText(R.string.start_time);
                }
                break;
            case R.id.previousButton:
                if(step != 0){
                    Intent previousIntent = new Intent(GamingActivity.this, GamingActivity.class);
                    previousIntent.putExtra(CommonUtils.LIST_NUM, step - 1);
                    startActivity(previousIntent);
                    finish();
                }
                break;
            case R.id.nextButton:
                if(step != CommonUtils.ruleList.size() - 1){
                    Intent nextIntent = new Intent(GamingActivity.this, GamingActivity.class);
                    nextIntent.putExtra(CommonUtils.LIST_NUM, step + 1);
                    startActivity(nextIntent);
                    finish();
                }else{
                    finish();
                }
                break;
            default:
        }
    }
    private void startPositiveTime(){
        positiveTimeCount = new TimeCount(positiveTimeInt * 1000, 1000);
        positiveTimeCount.start();
    }

    private void startNegativeTime(){
        negativeTimeCount = new TimeCount(negativeTimeInt * 1000, 1000);
        negativeTimeCount.start();
    }

    private void changePositiveTime(long millisUntilFinished){
        positiveTimeInt = (int)millisUntilFinished / 1000;
        positiveTime.setText(positiveTimeInt + "秒");
        showTime.setText(positiveTimeInt + "秒");
        remindTime(positiveTimeInt);
    }
    private void changeNegativeTime(long millisUntilFinished){
        negativeTimeInt = (int)millisUntilFinished / 1000;
        negativeTime.setText(negativeTimeInt + "秒");
        showTime.setText(negativeTimeInt + "秒");
        remindTime(negativeTimeInt);
    }
    private void remindTime(int time){
        if(time == 30 && remindTime){
            Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(400);
        }
    }

    private void switchButtonClickState(boolean state){
        if(state){
            switchButton.setClickable(true);
            switchButton.setTextColor(Color.BLACK);
        }else {
            switchButton.setClickable(false);
            switchButton.setTextColor(Color.GRAY);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (negativeTimeCount != null)
            negativeTimeCount.cancel();
        if (positiveTimeCount != null)
            positiveTimeCount.cancel();
        if(keyCode == KeyEvent.KEYCODE_MENU){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @Override
    public void onBackPressed() {
        if (!isExit) {
            Toast.makeText(this, R.string.click_to_exit, Toast.LENGTH_SHORT).show();
            isExit = true;
            mHandler.removeMessages(EXIT_TIME);
            mHandler.sendEmptyMessageDelayed(EXIT_TIME, 2000);
        } else {
            finish();
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            settingButton.setClickable(false);
            switch (timeModel){
                case CommonUtils.BOTH:
                    if(bothTag == CommonUtils.POSITIVE){
                        positiveTime.setText("正方时间到");
                        showTime.setText("正方时间到");
                    }else if(bothTag == CommonUtils.NEGATIVE){
                        negativeTime.setText("反方时间到");
                        showTime.setText("反方时间到");
                    }
                    GamingActivity.this.switchButtonClickState(false);
                    break;
                case CommonUtils.POSITIVE:
                    positiveTime.setText("时间到");
                    showTime.setText("时间到");
                    break;
                case CommonUtils.NEGATIVE:
                    positiveTime.setText("时间到");
                    showTime.setText("时间到");
                    break;
                default:
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            Log.e("SXY","剩余" + millisUntilFinished/1000);
            switch (timeModel){
                case CommonUtils.BOTH:
                    if(bothTag == CommonUtils.POSITIVE){
                        changePositiveTime(millisUntilFinished);
                    }else if(bothTag == CommonUtils.NEGATIVE){
                        changeNegativeTime(millisUntilFinished);
                    }
                    break;
                case CommonUtils.POSITIVE:
                    changePositiveTime(millisUntilFinished);
                    break;
                case CommonUtils.NEGATIVE:
                    changeNegativeTime(millisUntilFinished);
                    break;
                default:
            }
        }
    }
}
