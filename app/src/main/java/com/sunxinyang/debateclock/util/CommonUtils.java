package com.sunxinyang.debateclock.util;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;

/**
 * Created by sunxinyang on 2017/2/20.
 */

public class CommonUtils {

    public final static int POSITIVE = 1;

    public final static int NEGATIVE = 2;

    public final static int BOTH = 3;

    public final static int BOTH_NOT = 0;

    public final static String LIST_NUM = "listNum";

    public final static String APP_PATH = "//debateClock";

    public static LinkedList<RuleSettingInfo> ruleList = new LinkedList<>();

    public static String[] readRuleFromFile(){


        File[] allFiles = new File(Environment.getExternalStorageDirectory().getPath() + CommonUtils.APP_PATH).listFiles();
        String[] filename = new String[allFiles.length];
        for(int i = 0; i < allFiles.length; i++){
            if(allFiles[i].isFile()){
                filename[i] = allFiles[i].getName();
            }
        }
        return filename;
    }

    public static boolean haveRuleFile(){
        return readRuleFromFile().length == 0 ? false : true;
    }

    public static LinkedList<RuleSettingInfo> readRuleList(){
        //不对不对逻辑错误
        String[] filename = readRuleFromFile();
        LinkedList<RuleSettingInfo> ruleInfoList = null;
        try {
            for(String useName : filename){
                ObjectInputStream objectInputStream = null;
                objectInputStream = new ObjectInputStream(new FileInputStream(Environment.getExternalStorageDirectory().getPath() + CommonUtils.APP_PATH + "//" + useName));
                ruleInfoList.add((RuleSettingInfo)objectInputStream.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ruleInfoList;
    }
}
