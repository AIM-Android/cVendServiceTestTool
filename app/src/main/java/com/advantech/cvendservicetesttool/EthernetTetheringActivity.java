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

//    private static final String ACTION_ETHTETHERING_ON = "com.advantech.aim75.ETHTETHERING_ON";
//    private static final String ACTION_ETHTETHERING_OFF = "com.advantech.aim75.ETHTETHERING_OFF";
//    private static final String ACTION_ETHTETHERING_STATUS = "com.advantech.aim75.ETHTETHERING_STATUS";
//    private static final String IS_ETHTETHERING_ON = "is_ethtethering_on";

    private static final String ACTION_ETHERNET_TETHERING = "com.advantech.intent.action.ETHERNET_TETHERING";
    private static final String EXTRA_ETHERNET_TETHERING_EVENT = "com.advantech.intent.extra.ETHERNET_TETHERING_EVENT";
    private static final String ETHERNET_TETHERING_EVENT_START = "START";
    private static final String ETHERNET_TETHERING_EVENT_STOP = "STOP";
    private static final String ACTION_ETHERNET_TETHERING_STATUS_CHANGED = "com.advantech.intent.action.ETHERNET_TETHERING_STATUS_CHANGED";
    private static final String EXTRA_ETHERNET_TETHERING_STATUS = "status";
    private static final int ETHERNET_TETHERING_STATUS_UNKNOWN = 0;
    private static final int ETHERNET_TETHERING_STATUS_FAILED = 1;
    private static final int ETHERNET_TETHERING_STATUS_UNAVAILABLE = 2;
    private static final int ETHERNET_TETHERING_STATUS_STARTED = 3;
    private static final int ETHERNET_TETHERING_STATUS_STOPED = 4;

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
        boolean status = ShareUtils.getBoolean(this, EXTRA_ETHERNET_TETHERING_STATUS, false);
        sw = findViewById(R.id.sw);
        sw.setChecked(status);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent();
                intent.setAction(ACTION_ETHERNET_TETHERING);
                intent.putExtra(EXTRA_ETHERNET_TETHERING_EVENT, isChecked ? ETHERNET_TETHERING_EVENT_START : ETHERNET_TETHERING_EVENT_STOP);
                sendBroadcast(intent);
            }
        });
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ETHERNET_TETHERING_STATUS_CHANGED);
        registerReceiver(broadcastReceiver, filter);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "action : " + action);
            if (ACTION_ETHERNET_TETHERING_STATUS_CHANGED.equals(action)) {
                int status = intent.getIntExtra(EXTRA_ETHERNET_TETHERING_STATUS, ETHERNET_TETHERING_STATUS_STOPED);
                Log.d(TAG, "status : " + status);
                if (ETHERNET_TETHERING_STATUS_STARTED == status) {
                    ShareUtils.putBoolean(context, EXTRA_ETHERNET_TETHERING_STATUS, true);
                    sw.setChecked(true);
                    ToastUtil.show(context, "ethernet tethering start success.", Gravity.CENTER, Toast.LENGTH_SHORT);
                } else if (ETHERNET_TETHERING_STATUS_FAILED == status) {
                    ShareUtils.putBoolean(context, EXTRA_ETHERNET_TETHERING_STATUS, false);
                    sw.setChecked(false);
                    ToastUtil.show(context, "ethernet tethering start failed.", Gravity.CENTER, Toast.LENGTH_SHORT);
                } else {
                    ShareUtils.putBoolean(context, EXTRA_ETHERNET_TETHERING_STATUS, false);
                    sw.setChecked(false);
                }
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