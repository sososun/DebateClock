package com.sunxinyang.debateclock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sunxinyang.debateclock.ui.GameRuleList;
import com.sunxinyang.debateclock.ui.GameRuleSettingActivity;
import com.sunxinyang.debateclock.util.CommonUtils;

public class GamePrepareActivity extends AppCompatActivity {

    Button button;
    EditText negativeTitle, positiveTitle, positiveName, negativeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEditAndSave()) {
                    CommonUtils.ruleList.clear();
                    if(CommonUtils.haveRuleFile()){
                        startActivity(new Intent(GamePrepareActivity.this, GameRuleList.class));
                    }else{
                        Intent intent = new Intent(GamePrepareActivity.this, GameRuleSettingActivity.class);
                        intent.putExtra(CommonUtils.LIST_NUM, 0);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(GamePrepareActivity.this, getText(R.string.game_setting_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initUI() {
        button = (Button) findViewById(R.id.button);
        positiveTitle = (EditText) findViewById(R.id.positiveTitleEdit);
        negativeTitle = (EditText) findViewById(R.id.negativeTitleEdit);
        positiveName = (EditText) findViewById(R.id.positiveNameEdit);
        negativeName = (EditText) findViewById(R.id.negativeNameEdit);
    }

    private boolean checkEditAndSave() {
        String positiveTitleString = positiveTitle.getText().toString().trim();
        String positiveNameString = positiveName.getText().toString().trim();
        String negativeTitleString = negativeTitle.getText().toString().trim();
        String negativeNameString = negativeName.getText().toString().trim();
        if (!positiveNameString.equals("") && !positiveTitleString.equals("") && !negativeTitleString.equals("") && !negativeNameString.equals("")) {
            SharedPreferences preferences = getSharedPreferences("game_title", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("positiveTitle", positiveTitleString);
            editor.putString("positiveName", positiveNameString);
            editor.putString("negativeTitle", negativeTitleString);
            editor.putString("negativeName", negativeNameString);
            editor.commit();
            return true;
        }
        return false;
    }
}
