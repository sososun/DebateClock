package com.sunxinyang.debateclock.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import com.sunxinyang.debateclock.R;
import com.sunxinyang.debateclock.util.CommonUtils;
import com.sunxinyang.debateclock.util.RuleSettingInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by sunxinyang on 2017/2/23.
 */

public class GameRuleList extends AppCompatActivity{

    ListViewCompat listViewCompat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rule_list_activity);
        RuleAdapt ruleAdapt = new RuleAdapt(readRuleFromFile());
        listViewCompat = (ListViewCompat) findViewById(R.id.list_view);
        listViewCompat.setAdapter(ruleAdapt);
    }
    private String[] readRuleFromFile(){
//            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(sdcardDir.getPath() + CommonUtils.APP_PATH + "//" + fileName));
//            LinkedList<RuleSettingInfo> sunxinyang = null;
//            sunxinyang = (LinkedList<RuleSettingInfo>) objectInputStream.readObject();

        File[] allFiles = new File(Environment.getExternalStorageDirectory().getPath() + CommonUtils.APP_PATH).listFiles();
        String[] filename = new String[allFiles.length];
        for(int i = 0; i < allFiles.length; i++){
            if(allFiles[i].isFile()){
                filename[i] = allFiles[i].getName();
            }
        }
        return filename;
    }

    class RuleAdapt extends BaseAdapter{

        String[] filename;

        public RuleAdapt(String[] filename){
            this.filename = filename;
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
            return null;
        }
    }
}
