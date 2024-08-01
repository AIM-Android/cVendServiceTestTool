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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OnewireExternalActivity extends AppCompatActivity {

    private static final String TAG = "OnewireExternalActivity";

    private static final String ACTION_ONEWIRE = "com.advantech.aim75.ONEWIRE";
    private static final String ACTION_ONEWIRE_RESULT = "com.advantech.aim75.ONEWIRE_RESULT";
    private static final String ONEWIRE_RESULT = "ReturnData";

    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onewire_external);

        initView();

        registerReceiver();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        resultText = findViewById(R.id.result_tv);

        Button button80 = findViewById(R.id.btn_80);
        button80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                broadcastOneWireExternalTo80();
            }
        });
        Button button81 = findViewById(R.id.btn_81);
        button81.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                broadcastOneWireExternalTo81();
            }
        });
    }

    private void broadcastOneWireExternalTo80() {
        Intent intent = new Intent();
        intent.setAction(ACTION_ONEWIRE);
        intent.putExtra("apccmd", "80");
        sendBroadcast(intent);
    }

    private void broadcastOneWireExternalTo81() {
        Intent intent = new Intent();
        intent.setAction(ACTION_ONEWIRE);
        intent.putExtra("apccmd", "81");
        sendBroadcast(intent);
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ONEWIRE_RESULT);
        registerReceiver(broadcastReceiver, filter);
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "action : " + action);
            if (ACTION_ONEWIRE_RESULT.equals(action)) {
                String result = intent.getStringExtra(ONEWIRE_RESULT);
                Log.d(TAG, "result : " + result);
                resultText.setText(result);
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
}