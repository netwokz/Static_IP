package com.netwokz.staticip;

import android.content.Context;
import android.util.Log;
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
        final StaticIpRecord tempRecord = mStaticIpList.get(i);
//        String type = mStaticIpList.get(i).mType;
        int type = tempRecord.mType;
        Log.d("CustomAdapter", String.valueOf(type));
        int resId;
        switch (type) {
//            case "Laptop":
            case 1:
                holder.mType.setImageResource(R.drawable.ic_laptop_24px);
//                resId = R.drawable.ic_laptop_24px;
                break;
//            case "Desktop":
            case 2:
                holder.mType.setImageResource(R.drawable.ic_desktop_windows_24px);
//                resId = R.drawable.ic_desktop_windows_24px;
                break;
//            case "Mobile":
            case 3:
                holder.mType.setImageResource(R.drawable.ic_phone_android_24px);
//                resId = R.drawable.ic_phone_android_24px;
                break;
//            case "Raspberry Pi":
            case 4:
                holder.mType.setImageResource(R.drawable.ic_devices_other_24px);
//                resId = R.drawable.ic_devices_other_24px;
                break;
            default:
                holder.mType.setImageResource(R.drawable.ic_device_unknown_24px);
//                resId = R.drawable.ic_device_unknown_24px;
        }
//        holder.mType.setImageResource(resId);
        holder.mIp.setText(tempRecord.mIpAddress);
        holder.mMac.setText(tempRecord.mMacAddress);
        holder.mName.setText(tempRecord.mName);
        holder.mDate.setText(tempRecord.mDateAdded);
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListener != null)
                    mListener.onCardLongClick(tempRecord);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStaticIpList.size();
    }
}
