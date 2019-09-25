package com.netwokz.staticip;

import com.orm.SugarRecord;

public class StaticIpRecord extends SugarRecord {

    String mIpAddress;
    String mMacAddress;
    String mType;
    String mName;
    String mDateAdded;


    public StaticIpRecord() {

    }

    public StaticIpRecord(String ip, String mac, String type, String name, String date) {

        this.mIpAddress = ip;
        this.mMacAddress = mac;
        this.mType = type;
        this.mName = name;
        this.mDateAdded = date;

    }
}
