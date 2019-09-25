package com.netwokz.staticip;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class StaticIpViewHolder extends RecyclerView.ViewHolder {

    CardView mCardView;
    TextView mIp;
    TextView mName;
    TextView mMac;
    TextView mDate;
    ImageView mType;

    StaticIpViewHolder(View itemView) {
        super(itemView);
        mCardView = itemView.findViewById(R.id.card_view);
        mIp = itemView.findViewById(R.id.tv_ip);
        mName = itemView.findViewById(R.id.tv_name);
        mMac = itemView.findViewById(R.id.tv_mac);
        mDate = itemView.findViewById(R.id.tv_date);
        mType = itemView.findViewById(R.id.iv_type);
        mCardView.setRadius(20);

    }
}
