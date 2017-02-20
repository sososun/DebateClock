package com.sunxinyang.debateclock.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sunxinyang.debateclock.R;
import com.sunxinyang.debateclock.util.CommonUtils;
import com.sunxinyang.debateclock.util.RuleSettingInfo;

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
        }
        listNum = getIntent().getIntExtra(CommonUtils.LIST_NUM, 0);
        showRule(listNum);
    }

    private void showRule(int listNum){
        if(listNum < CommonUtils.ruleList.size()){
            stageNameEdit.setText(CommonUtils.ruleList.get(listNum).stageName);
            checkBox.setChecked(CommonUtils.ruleList.get(listNum).tips);
            switch (CommonUtils.ruleList.get(listNum).timeModel){
                case CommonUtils.BOTH:
                    bothRadioButton.setChecked(true);
                    positiveTimeEdit.setText(CommonUtils.ruleList.get(listNum).positiveTime);
                    negativeTimeEdit.setText(CommonUtils.ruleList.get(listNum).negativeTime);
                    choiceTimeUI(CommonUtils.BOTH);
                    break;
                case CommonUtils.POSITIVE:
                    positiveRadioButton.setChecked(true);
                    positiveTimeEdit.setText(CommonUtils.ruleList.get(listNum).positiveTime);
                    choiceTimeUI(CommonUtils.POSITIVE);
                    break;
                case CommonUtils.NEGATIVE:
                    negativeRadioButton.setChecked(true);
                    negativeTimeEdit.setText(CommonUtils.ruleList.get(listNum).negativeTime);
                    choiceTimeUI(CommonUtils.NEGATIVE);
                    break;
                default:
                    choiceTimeUI(CommonUtils.BOTH_NOT);
            }
        }
    }

    private boolean isFirst() {
        return getIntent().getIntExtra(CommonUtils.LIST_NUM, 0) == 0 ? true : false;
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
                Intent previousIntent = new Intent(GameRuleSettingActivity.this, GameRuleSettingActivity.class);
                previousIntent.putExtra(CommonUtils.LIST_NUM,listNum - 1);
                startActivity(previousIntent);
                finish();
                break;
            case R.id.nextButton:
                if (saveSetting()){
                    Intent nextIntent = new Intent(GameRuleSettingActivity.this, GameRuleSettingActivity.class);
                    nextIntent.putExtra(CommonUtils.LIST_NUM,listNum + 1);
                    startActivity(nextIntent);
                    finish();
                }else{
                    Toast.makeText(this,getText(R.string.game_setting_toast),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.finishButton:
                if (saveSetting()){
                    Intent finishIntent = new Intent(GameRuleSettingActivity.this, GamingActivity.class);
                    finishIntent.putExtra(CommonUtils.LIST_NUM, 0);
                    startActivity(finishIntent);
                    finish();
                }else{
                    Toast.makeText(this,getText(R.string.game_setting_toast),Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private boolean saveSetting() {

        RuleSettingInfo ruleSettingInfo = new RuleSettingInfo();
        String stageName = stageNameEdit.getText().toString().trim();
        String positiveTime = positiveTimeEdit.getText().toString().trim();
        String negativeTime = negativeTimeEdit.getText().toString().trim();

        if("".equals(stageName) || timeModel == CommonUtils.BOTH_NOT){
            return false;
        }
        if(timeModel == CommonUtils.POSITIVE && "".equals(positiveTime)){
            return false;
        }
        if(timeModel == CommonUtils.NEGATIVE && "".equals(negativeTime)){
            return false;
        }
        if(timeModel == CommonUtils.BOTH && "".equals(positiveTime) && "".equals(negativeTime)){
            return false;
        }

        ruleSettingInfo.stageName = stageName;
        ruleSettingInfo.timeModel = timeModel;
        ruleSettingInfo.positiveTime = positiveTime;
        ruleSettingInfo.negativeTime = negativeTime;
        ruleSettingInfo.tips = checkBox.isChecked() ? true : false;
        if(listNum < CommonUtils.ruleList.size()){
            CommonUtils.ruleList.remove(listNum);
            CommonUtils.ruleList.add(listNum, ruleSettingInfo);
        }else {
            CommonUtils.ruleList.add(listNum, ruleSettingInfo);
        }
        Toast.makeText(this, "链表长度为： " + CommonUtils.ruleList.size(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
