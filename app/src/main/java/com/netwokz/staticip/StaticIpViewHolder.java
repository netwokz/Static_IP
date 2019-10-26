package com.netwokz.staticip;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class StaticIpViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

    CardView mCardView;
    TextView mIp;
    TextView mName;
    TextView mMac;
    TextView mDate;
    ImageView mType;

    StaticIpViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
        mCardView = itemView.findViewById(R.id.card_view);
        mIp = itemView.findViewById(R.id.tv_ip);
        mName = itemView.findViewById(R.id.tv_name);
        mMac = itemView.findViewById(R.id.tv_mac);
        mDate = itemView.findViewById(R.id.tv_date);
        mType = itemView.findViewById(R.id.iv_type);
        mCardView.setRadius(20);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select Action");
        MenuItem edit = menu.add(Menu.NONE, 1, 1, "Edit");
        MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete");

        edit.setOnMenuItemClickListener(onChange);
        delete.setOnMenuItemClickListener(onChange);
    }

    @Override
    public void onClick(View v) {

    }

    private final MenuItem.OnMenuItemClickListener onChange = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case 1:

                    return true;
                case 2:

                    return true;
            }
            return false;
        }
    };

}
