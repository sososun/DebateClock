package com.sunxinyang.debateclock.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sunxinyang.debateclock.R;
import com.sunxinyang.debateclock.util.CommonUtils;

import java.io.File;

/**
 * Created by sunxinyang on 2017/2/23.
 */

public class GameRuleList extends AppCompatActivity{

    ListViewCompat listViewCompat;
    Button noChoose;
    String[] filename;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rule_list_activity);
        filename = CommonUtils.readRuleFromFile();
        RuleAdapt ruleAdapt = new RuleAdapt(filename, this);
        listViewCompat = (ListViewCompat) findViewById(R.id.list_view);
        listViewCompat.setAdapter(ruleAdapt);
        noChoose = (Button) findViewById(R.id.no_choose);
        listViewCompat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                createDialog(position);
            }
        });
        noChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameRuleList.this, GameRuleSettingActivity.class));
            }
        });
    }

    private void createDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(String.format(getString(R.string.choose_dialog_message), filename[position]));
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

    class RuleAdapt extends BaseAdapter{

        String[] filename;
        Context context;

        public RuleAdapt(String[] filename, Context context){
            this.filename = filename;
            this.context = context;
        }

        @Override
        public int getCount() {
            return filename.length;
        }

        @Override
        public Object getItem(int position) {
            return filename[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.rule_list_item, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.file_name);
            textView.setText(filename[position]);

            return convertView;
        }
    }
}
