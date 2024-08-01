package com.advantech.cvendservicetesttool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class BCRGPIOPinActivity extends AppCompatActivity {

    private static final String TAG = "EthernetTetheringActivity";

    private static final String ACTION_BCRPIN_HIGH = "com.advantech.aim75.BCRPIN_HIGH";
    private static final String ACTION_BCRPIN_LOW = "com.advantech.aim75.BCRPIN_LOW";

    private Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcrgpiopin);

        initView();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        sw = findViewById(R.id.bcr_gpio_pin_sw);
        boolean status = ShareUtils.getBoolean(this, "bcr", false);
        sw.setChecked(status);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShareUtils.putBoolean(BCRGPIOPinActivity.this, "bcr", isChecked);
                Intent intent = new Intent();
                intent.setAction(isChecked ? ACTION_BCRPIN_LOW : ACTION_BCRPIN_HIGH);
                sendBroadcast(intent);
            }
        });
    }

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