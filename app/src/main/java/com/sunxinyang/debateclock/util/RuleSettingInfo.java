package com.sunxinyang.debateclock.util;

import java.io.Serializable;

/**
 * Created by soso-sun on 2017/2/20.
 */

public class RuleSettingInfo implements Serializable{

    public String stageName;

    public int timeModel;

    public int positiveTime;

    public int negativeTime;

    public boolean tips;
}
