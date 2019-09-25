package com.netwokz.staticip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<StaticIpViewHolder> {

    List<StaticIpRecord> mStaticIpList;
    private OnCardLongClickListener mListener;
    Context mContext;

    public interface OnCardLongClickListener {
        // These methods are the different events and
        // need to pass relevant arguments related to the event triggered
        void onCardLongClick(StaticIpRecord staticIpRecord);
    }

    public void setOnCardClickListener(OnCardLongClickListener listener) {
        mListener = listener;
    }

    public CustomAdapter(Context context, List<StaticIpRecord> staticIps) {
        mContext = context;
        mStaticIpList = staticIps;
    }

    @Override
    public StaticIpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        StaticIpViewHolder staticIpViewHolder = new StaticIpViewHolder(view);
        return staticIpViewHolder;
    }

    //"Desktop","Laptop","Mobile","Raspberry Pi","Other"

    @Override
    public void onBindViewHolder(final StaticIpViewHolder holder, final int i) {
        String type = mStaticIpList.get(i).mType;
        int resId;
        switch (type) {
            case "Laptop":
                resId = R.drawable.ic_laptop_24px;
                break;
            case "Desktop":
                resId = R.drawable.ic_desktop_windows_24px;
                break;
            case "Mobile":
                resId = R.drawable.ic_phone_android_24px;
                break;
            case "Raspberry Pi":
                resId = R.drawable.ic_devices_other_24px;
                break;
            default:
                resId = R.drawable.ic_device_unknown_24px;
        }
        holder.mType.setImageResource(resId);
        holder.mIp.setText(String.valueOf(mStaticIpList.get(i).mIpAddress));
        holder.mMac.setText(mStaticIpList.get(i).mMacAddress);
        holder.mName.setText(mStaticIpList.get(i).mName);
        holder.mDate.setText(mStaticIpList.get(i).mDateAdded);
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListener != null)
                    mListener.onCardLongClick(mStaticIpList.get(i));
                return true;
            }
        });

    }

    public void updateView(List<StaticIpRecord> list) {
        mStaticIpList.clear();
        mStaticIpList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mStaticIpList.size();
    }
}
