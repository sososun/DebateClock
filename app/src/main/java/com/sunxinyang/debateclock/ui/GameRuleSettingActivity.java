package com.sunxinyang.debateclock.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by soso-sun on 2017/2/18.
 */

public class GameRuleSettingActivity extends AppCompatActivity implements View.OnClickListener {


    RelativeLayout previousRelativeLayout, negativeRelativeLayout;
    Button previousButton, nextButton, finishButton;
    EditText stageNameEdit, positiveTimeEdit, negativeTimeEdit;
    RadioButton positiveRadioButton, negativeRadioButton, bothRadioButton;
    CheckBox checkBox;
    private boolean isExit = false;
    private int listNum = 0;
    private int timeModel = CommonUtils.BOTH_NOT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rule_setting);
        initUI();
        buttonClick();
        choiceTimeUI(CommonUtils.BOTH_NOT);
        if (isFirst()) {
            if(CommonUtils.ruleList.size() != 0){
                CommonUtils.ruleList.clear();
            }
        }
        listNum = getIntent().getIntExtra(CommonUtils.LIST_NUM, CommonUtils.FIRST_INTO_RULE_SETTING);
        if(listNum < 1){
            previousButton.setVisibility(View.GONE);
        }
        showRule(listNum);
        if(listNum == CommonUtils.FIRST_INTO_RULE_SETTING){
            listNum = listNum + 1;
        }
    }

    private void showRule(int listNum) {
        int i = CommonUtils.ruleList.size();
        if (listNum < CommonUtils.ruleList.size() && listNum != CommonUtils.FIRST_INTO_RULE_SETTING) {
            stageNameEdit.setText(CommonUtils.ruleList.get(listNum).stageName);
            checkBox.setChecked(CommonUtils.ruleList.get(listNum).tips);
            switch (CommonUtils.ruleList.get(listNum).timeModel) {
                case CommonUtils.BOTH:
                    choiceTimeUI(CommonUtils.BOTH);
                    bothRadioButton.setChecked(true);
                    positiveTimeEdit.setText(String.valueOf(CommonUtils.ruleList.get(listNum).positiveTime));
                    negativeTimeEdit.setText(String.valueOf(CommonUtils.ruleList.get(listNum).negativeTime));
                    break;
                case CommonUtils.POSITIVE:
                    choiceTimeUI(CommonUtils.POSITIVE);
                    positiveRadioButton.setChecked(true);
                    positiveTimeEdit.setText(String.valueOf(CommonUtils.ruleList.get(listNum).positiveTime));
                    break;
                case CommonUtils.NEGATIVE:
                    choiceTimeUI(CommonUtils.NEGATIVE);
                    negativeRadioButton.setChecked(true);
                    negativeTimeEdit.setText(String.valueOf(CommonUtils.ruleList.get(listNum).negativeTime));
                    break;
                default:
                    choiceTimeUI(CommonUtils.BOTH_NOT);
            }
        }
    }

    private boolean isFirst() {
        return getIntent().getIntExtra(CommonUtils.LIST_NUM, CommonUtils.FIRST_INTO_RULE_SETTING) == CommonUtils.FIRST_INTO_RULE_SETTING ? true : false;
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
                previousIntent.putExtra(CommonUtils.LIST_NUM, listNum - 1);
                startActivity(previousIntent);
                finish();
                break;
            case R.id.nextButton:
                if (saveSetting()) {
                    Intent nextIntent = new Intent(GameRuleSettingActivity.this, GameRuleSettingActivity.class);
                    nextIntent.putExtra(CommonUtils.LIST_NUM, listNum + 1);
                    startActivity(nextIntent);
                    finish();
                } else {
                    Toast.makeText(this, getText(R.string.game_setting_toast), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.finishButton:
                if (saveSetting()) {
                    createSaveDialog();
                } else {
                    Toast.makeText(this, getText(R.string.game_setting_toast), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void startToRuleDetailList(){
        Intent intent = new Intent(GameRuleSettingActivity.this, RuleDetailList.class);
        startActivity(intent);
        finish();
    }
    private void createSaveDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        editText.setHint(R.string.save_file_edittext);
        builder.setMessage(R.string.save_file_message);
        builder.setView(editText);
        builder.setPositiveButton(R.string.save_file_confirm, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(saveToFile(editText.getText().toString().trim() + ".txt")){
                    Toast.makeText(GameRuleSettingActivity.this, R.string.save_file_toast, Toast.LENGTH_SHORT).show();
                    startToRuleDetailList();
                }else {
                    Toast.makeText(GameRuleSettingActivity.this, R.string.save_file_failed_toast, Toast.LENGTH_SHORT).show();
                    createSaveDialog();
                }
            }
        });
        builder.setNegativeButton(R.string.save_file_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startToRuleDetailList();
            }
        });
        builder.create();
        builder.show();
    }
    private boolean saveToFile(String fileName) {
        File sdcardDir = Environment.getExternalStorageDirectory();
        File file = new File(sdcardDir.getPath() + CommonUtils.APP_PATH);
        if (!file.exists()) {
            //若不存在，创建目录，可以在应用启动的时候创建
            file.mkdir();
        }
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(sdcardDir.getPath() + CommonUtils.APP_PATH + "//" + fileName));
            objectOutputStream.writeObject(CommonUtils.ruleList);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean saveSetting() {

        RuleSettingInfo ruleSettingInfo = new RuleSettingInfo();
        String stageName = stageNameEdit.getText().toString().trim();
        String positiveTime = positiveTimeEdit.getText().toString().trim();
        String negativeTime = negativeTimeEdit.getText().toString().trim();

        if ("".equals(stageName) || timeModel == CommonUtils.BOTH_NOT) {
            return false;
        }
        if (timeModel == CommonUtils.POSITIVE && "".equals(positiveTime)) {
            return false;
        }
        if (timeModel == CommonUtils.NEGATIVE && "".equals(negativeTime)) {
            return false;
        }
        if (timeModel == CommonUtils.BOTH && "".equals(positiveTime) && "".equals(negativeTime)) {
            return false;
        }

        ruleSettingInfo.stageName = stageName;
        ruleSettingInfo.timeModel = timeModel;

        ruleSettingInfo.positiveTime = "".equals(positiveTime) ? 0 : Integer.parseInt(positiveTime);
        ruleSettingInfo.negativeTime = "".equals(negativeTime) ? 0 : Integer.parseInt(negativeTime);
        ruleSettingInfo.tips = checkBox.isChecked() ? true : false;
        if (listNum < CommonUtils.ruleList.size()) {
            CommonUtils.ruleList.remove(listNum);
            CommonUtils.ruleList.add(listNum, ruleSettingInfo);
        } else {
            CommonUtils.ruleList.add(listNum, ruleSettingInfo);
        }
//        Toast.makeText(this, "链表长度为： " + CommonUtils.ruleList.size(), Toast.LENGTH_SHORT).show();
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
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
            mHandler.removeMessages(CommonUtils.EXIT_TIME);
            mHandler.sendEmptyMessageDelayed(CommonUtils.EXIT_TIME, 2000);
        } else {
            finish();
        }
    }
}
