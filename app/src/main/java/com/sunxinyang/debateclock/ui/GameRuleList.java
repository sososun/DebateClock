package com.sunxinyang.debateclock.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sunxinyang.debateclock.R;
import com.sunxinyang.debateclock.util.CommonUtils;

import java.io.File;

/**
 * Created by sunxinyang on 2017/2/23.
 */

public class GameRuleList extends AppCompatActivity{

    ListViewCompat listViewCompat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rule_list_activity);
        RuleAdapt ruleAdapt = new RuleAdapt(CommonUtils.readRuleFromFile(), this);
        listViewCompat = (ListViewCompat) findViewById(R.id.list_view);
        listViewCompat.setAdapter(ruleAdapt);
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
