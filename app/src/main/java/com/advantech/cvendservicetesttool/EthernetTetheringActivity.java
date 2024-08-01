package com.advantech.cvendservicetesttool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class EthernetTetheringActivity extends AppCompatActivity {

    private static final String TAG = "EthernetTetheringActivity";

    private static final String ACTION_ETHTETHERING_ON = "com.advantech.aim75.ETHTETHERING_ON";
    private static final String ACTION_ETHTETHERING_OFF = "com.advantech.aim75.ETHTETHERING_OFF";
    private static final String ACTION_ETHTETHERING_STATUS = "com.advantech.aim75.ETHTETHERING_STATUS";
    private static final String IS_ETHTETHERING_ON = "is_ethtethering_on";

    private Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethernet_tethering);

        initView();

        registerReceiver();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        sw = findViewById(R.id.sw);
        boolean status = ShareUtils.getBoolean(this, IS_ETHTETHERING_ON, false);
        sw.setChecked(status);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShareUtils.putBoolean(EthernetTetheringActivity.this, IS_ETHTETHERING_ON, isChecked);
                Intent intent = new Intent();
                intent.setAction(isChecked ? ACTION_ETHTETHERING_ON : ACTION_ETHTETHERING_OFF);
                sendBroadcast(intent);
            }
        });
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ETHTETHERING_STATUS);
        registerReceiver(broadcastReceiver, filter);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "action : " + action);
            if (ACTION_ETHTETHERING_STATUS.equals(action)) {
                boolean status = intent.getBooleanExtra(IS_ETHTETHERING_ON, false);
                Log.d(TAG, "status : " + status);
                ShareUtils.putBoolean(context, IS_ETHTETHERING_ON, status);
                ToastUtil.show(context, status ? "success" : "fail", Gravity.CENTER, Toast.LENGTH_SHORT);
                sw.setChecked(status);
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}