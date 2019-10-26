package com.netwokz.staticip;

import com.orm.SugarRecord;

public class StaticIpRecord extends SugarRecord {

    String mIpAddress;
    String mMacAddress;
    int mType;
    String mName;
    String mDateAdded;


    public StaticIpRecord() {

    }

    public StaticIpRecord(StaticIpRecord record) {

        this.mIpAddress = record.mIpAddress;
        this.mMacAddress = record.mMacAddress;
        this.mType = record.mType;
        this.mName = record.mName;
        this.mDateAdded = record.mDateAdded;

    }

    public StaticIpRecord(String ip, String mac, int type, String name, String date) {

        this.mIpAddress = ip;
        this.mMacAddress = mac;
        this.mType = type;
        this.mName = name;
        this.mDateAdded = date;

    }
}
