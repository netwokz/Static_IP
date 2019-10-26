package com.netwokz.staticip;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private List<StaticIpRecord> data;
    static View.OnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        if (StaticIpRecord.count(StaticIpRecord.class) != -1)
        data = getStaticIpList();
        Log.d("MainActivity", "initial data size: " + data.size());

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CustomAdapter(this, data);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewIpDialog();
                Log.d("MainActivity", "FAB onClick data size: " + data.size());
            }
        });
    }

    private List<StaticIpRecord> getStaticIpList() {
        List<StaticIpRecord> mList = StaticIpRecord.listAll(StaticIpRecord.class);
        return mList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                adapter.notifyDataSetChanged();
                break;
//            case R.id.action_refresh:
//                mAdapter.notifyDataSetChanged();
//                break;
            case R.id.action_settings:
                Intent i = new Intent(this, Preferences.class);
                startActivity(i);
                break;
            case R.id.action_add_generated_car:
                data.add(generateRandomEntry());
                adapter.notifyDataSetChanged();
                break;
            case R.id.action_clear_db:
                StaticIpRecord.deleteAll(StaticIpRecord.class);
                data.clear();
//                List<StaticIpRecord> mList = getStaticIpList();
//                for (int x = 0; x < mList.size(); x++) {
//                    StaticIpRecord mRecord = mList.get(x);
//                    mRecord.delete();
//                    data.remove(x);
//                }
                adapter.notifyDataSetChanged();
            default:
                break;
        }
        return true;
    }

    public String getRandomIp() {
        String mRandomIp;
        Random random = new Random();
        int x = random.nextInt(155) + 100;
        mRandomIp = "192.168.0." + x;
        return mRandomIp;
    }

    public String getRandomName() {
        String mType;
        String types[] = {"My Main PC", "My Laptop", "My Pixel 3 XL", "Garage Pi", "Pi Hole", "Other"};
        Random rand = new Random();
        mType = types[rand.nextInt(types.length)];
        return mType;
    }

    public String getRandomMacAddress() {
        String mac = "";
        Random r = new Random();
        for (int i = 0; i < 6; i++) {
            int n = r.nextInt(255);
            mac += String.format("%02x", n);
        }
        String finishedMac = mac.toUpperCase().replaceAll("..(?!$)", "$0:");

        return finishedMac;
    }

    public String getRandomDate() {
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());

        return simpleDateFormat.format(new Date());
    }

    public StaticIpRecord generateRandomEntry() {
        String mName = getRandomName();
        int mType;
        switch (mName) {
            case "My Main PC":
                mType = 1;
                break;
            case "My Laptop":
                mType = 2;
                break;
            case "My Pixel 3 XL":
                mType = 3;
                break;
            case "Garage Pi":
                mType = 4;
                break;
            case "Pi Hole":
                mType = 5;
                break;
            default:
                mType = 6;
        }

        StaticIpRecord record = new StaticIpRecord(getRandomIp(), getRandomMacAddress(), mType, mName, getRandomDate());
        record.save();
        return record;
    }

    private void showNewIpDialog() {
        FragmentManager fm = getFragmentManager();
        NewStaticIpDialog newDialogFragment = NewStaticIpDialog.newInstance();
        newDialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                updateIpListView();
            }
        });
        newDialogFragment.show(fm, "fragment_new_ip");
    }

    public void showEditIpDialog(int id) {
        Bundle bundle = new Bundle();
        bundle.putLong("id", data.get(id).getId());
        FragmentManager fm = getFragmentManager();
        EditIpDialog editIpDialog = EditIpDialog.newInstance();
        editIpDialog.setArguments(bundle);
        editIpDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                updateIpListView();
            }
        });
        editIpDialog.show(fm, "fragment_edit_ip");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
//                displayMessage("Edit Item");
                showEditIpDialog(item.getGroupId());
                return true;
            case 2:
//                displayMessage("Delete Item");
                Log.d("MainActivity", "Delete Item " + item.getGroupId() + " ID: " + data.get(item.getGroupId()).getId());
                deleteIpRecord(data.get(item.getGroupId()).getId(), item.getGroupId());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void deleteIpRecord(final long recordId, final int position) {
        final StaticIpRecord oldRecord = StaticIpRecord.findById(StaticIpRecord.class, recordId);
        data.remove(position);
        oldRecord.delete();
        adapter.notifyDataSetChanged();
        final Snackbar snackbar = Snackbar
                .make(findViewById(R.id.root_view), "Record " + position + " is removed", Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new StaticIpRecord(oldRecord).save();
                        data.add(position, oldRecord);
                        adapter.notifyDataSetChanged();
                        Snackbar snackbar1 = Snackbar.make(findViewById(R.id.root_view), "Record restored.", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
        snackbar.show();
    }

    public void updateIpListView() {
        data.clear();
        data.addAll(getStaticIpList());
        adapter.notifyDataSetChanged();
    }

}
