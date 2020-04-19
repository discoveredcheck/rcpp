package com.example.ledcontrolserial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.things.pio.PeripheralManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PeripheralManager pioManager = PeripheralManager.getInstance();

        /*try {
            PeripheralManager pioManager = PeripheralManager.getInstance();
            Log.i(TAG, "Available UART: " + pioManager.getUartDeviceList());
        }catch (Exception e){
            Log.i(TAG, e.toString());
        }*/


        setContentView(R.layout.activity_main);
        Switch sw = (Switch) findViewById(R.id.led_switch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String status;
                if (isChecked) {
                    status = "On";
                } else {
                    status = "Off";
                }
                Toast.makeText(getApplicationContext(), "Switched  " + status, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
