package com.sunxinyang.betaclock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by soso-sun on 2017/2/18.
 */

public class GameRuleSettingActivity extends AppCompatActivity {

    Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_rule_setting);
        finishButton = (Button) findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameRuleSettingActivity.this,GamingActivity.class));
            }
        });
    }
}
