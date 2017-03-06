package com.sunxinyang.debateclock.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sunxinyang.debateclock.R;
import com.sunxinyang.debateclock.util.CommonUtils;
import com.sunxinyang.debateclock.util.RuleSettingInfo;

import java.util.LinkedList;

/**
 * Created by soso-sun on 2017/3/5.
 */

public class ruleDetailList extends AppCompatActivity implements View.OnClickListener{

    ListViewCompat listViewCompat;
    Button useButton, nouseButton;
    DetailAdapt detailAdapt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rule_detail_activity);
        listViewCompat = (ListViewCompat) findViewById(R.id.rule_detail_list);
        useButton = (Button) findViewById(R.id.use_rule);
        nouseButton = (Button) findViewById(R.id.no_use_rule);
        useButton.setOnClickListener(this);
        nouseButton.setOnClickListener(this);
        detailAdapt = new DetailAdapt(this, CommonUtils.readRuleList());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.use_rule:
                break;
            case R.id.no_use_rule:
                break;
            default:
        }
    }

    class DetailAdapt extends BaseAdapter{

        Context context;
        LinkedList<RuleSettingInfo> ruleInfoList;


        public DetailAdapt(Context context, LinkedList<RuleSettingInfo> ruleInfoList){
            this.context = context;
            this.ruleInfoList = ruleInfoList;
        }

        @Override
        public int getCount() {
            return ruleInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return ruleInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.rule_detail_item, null);
            }
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView message = (TextView) convertView.findViewById(R.id.message);
            title.setText();
            return null;
        }
    }
}
