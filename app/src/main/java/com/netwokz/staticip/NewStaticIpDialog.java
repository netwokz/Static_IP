package com.netwokz.staticip;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewStaticIpDialog extends DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText mIpEdit;
    private EditText mMacEdit;
    private EditText mTypeEdit;
    private EditText mNameEdit;
    int mType;

    private Button mSaveButton;
    private Button mCancelButton;

    public NewStaticIpDialog() {

    }

    public static NewStaticIpDialog newInstance() {
        NewStaticIpDialog mFragment = new NewStaticIpDialog();
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.new_ip_dialog, container);
        mCancelButton = mView.findViewById(R.id.btn_cancel);
        mSaveButton = mView.findViewById(R.id.btn_save);
        mCancelButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);

        mIpEdit = mView.findViewById(R.id.et_ip);
        mMacEdit = mView.findViewById(R.id.et_mac);
        mNameEdit = mView.findViewById(R.id.et_name);

        Spinner spinner = mView.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.device_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_save:
                saveRecord();
                break;
        }
    }

    private void saveRecord() {
        String mIp = mIpEdit.getText().toString();
        String mMac = mMacEdit.getText().toString();
        String mName = mNameEdit.getText().toString();
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        String mDate = simpleDateFormat.format(new Date());

        StaticIpRecord newStaticIpRecord = new StaticIpRecord(mIp, mMac, mType, mName, mDate);
        newStaticIpRecord.save();
        dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                mType = 1;
                break;
            case 1:
                mType = 2;
                break;
            case 2:
                mType = 3;
                break;
            case 3:
                mType = 4;
                break;
            default:
                mType = 5;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


