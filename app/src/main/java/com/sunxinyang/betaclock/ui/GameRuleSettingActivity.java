package com.sunxinyang.betaclock.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.sunxinyang.betaclock.R;
import com.sunxinyang.betaclock.util.CommonUtils;
import com.sunxinyang.betaclock.util.RuleSettingInfo;

/**
 * Created by soso-sun on 2017/2/18.
 */

public class GameRuleSettingActivity extends AppCompatActivity implements View.OnClickListener {


    RelativeLayout previousRelativeLayout, negativeRelativeLayout;
    Button previousButton, nextButton, finishButton;
    EditText stageNameEdit, positiveTimeEdit, negativeTimeEdit;
    RadioButton positiveRadioButton, negativeRadioButton, bothRadioButton;
    CheckBox checkBox;
    private int listNum;
    private int timeModel = CommonUtils.BOTH_NOT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rule_setting);
        initUI();
        buttonClick();
        choiceTimeUI(CommonUtils.BOTH_NOT);
        if (isFirst()) {
            previousButton.setVisibility(View.GONE);
            listNum = 0;
        }
    }

    private boolean isFirst() {
        return "Start gaming".equals(getIntent().getAction()) ? true : false;
    }

    private void initUI() {
        previousRelativeLayout = (RelativeLayout) findViewById(R.id.positiveRelativeLayout);
        negativeRelativeLayout = (RelativeLayout) findViewById(R.id.negativeRelativeLayout);
        stageNameEdit = (EditText) findViewById(R.id.stageNameEdit);
        positiveTimeEdit = (EditText) findViewById(R.id.positiveTimeEdit);
        negativeTimeEdit = (EditText) findViewById(R.id.negativeTimeEdit);
        previousButton = (Button) findViewById(R.id.previousButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        finishButton = (Button) findViewById(R.id.finishButton);
        positiveRadioButton = (RadioButton) findViewById(R.id.positiveRadioButton);
        negativeRadioButton = (RadioButton) findViewById(R.id.negativeRadioButton);
        bothRadioButton = (RadioButton) findViewById(R.id.bothRadioButton);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
    }

    private void choiceTimeUI(int tag) {
        if (tag == CommonUtils.POSITIVE) {
            previousRelativeLayout.setVisibility(View.VISIBLE);
            negativeRelativeLayout.setVisibility(View.GONE);
            checkBox.setVisibility(View.VISIBLE);
        } else if (tag == CommonUtils.NEGATIVE) {
            previousRelativeLayout.setVisibility(View.GONE);
            negativeRelativeLayout.setVisibility(View.VISIBLE);
            checkBox.setVisibility(View.VISIBLE);
        } else if (tag == CommonUtils.BOTH) {
            previousRelativeLayout.setVisibility(View.VISIBLE);
            negativeRelativeLayout.setVisibility(View.VISIBLE);
            checkBox.setVisibility(View.VISIBLE);
        } else if (tag == CommonUtils.BOTH_NOT) {
            previousRelativeLayout.setVisibility(View.GONE);
            negativeRelativeLayout.setVisibility(View.GONE);
            checkBox.setVisibility(View.GONE);
        }
        timeModel = tag;
    }

    private void buttonClick() {
        positiveRadioButton.setOnClickListener(this);
        negativeRadioButton.setOnClickListener(this);
        bothRadioButton.setOnClickListener(this);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        finishButton.setOnClickListener(this);
        checkBox.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.positiveRadioButton:
                choiceTimeUI(CommonUtils.POSITIVE);
                break;
            case R.id.negativeRadioButton:
                choiceTimeUI(CommonUtils.NEGATIVE);
                break;
            case R.id.bothRadioButton:
                choiceTimeUI(CommonUtils.BOTH);
                break;
            case R.id.previousButton:
                break;
            case R.id.nextButton:

                startActivity(new Intent(GameRuleSettingActivity.this, GameRuleSettingActivity.class));
                finish();
                break;
            case R.id.finishButton:
                startActivity(new Intent(GameRuleSettingActivity.this, GamingActivity.class));
                finish();
                break;
            case R.id.checkBox:
                break;
            default:
        }
    }

    private boolean saveSetting() {
        RuleSettingInfo ruleSettingInfo = new RuleSettingInfo();
        ruleSettingInfo.stageName = stageNameEdit.getText().toString().trim();
        ruleSettingInfo.timeModel = timeModel;
        ruleSettingInfo.positiveTime = "";
        ruleSettingInfo.negativeTime = "";
        ruleSettingInfo.tips = false;
        return false;
//        CommonUtils.ruleList.add
    }
}
