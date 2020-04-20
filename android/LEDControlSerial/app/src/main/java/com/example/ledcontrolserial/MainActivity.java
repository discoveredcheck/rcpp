package com.example.ledcontrolserial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;
import android.hardware.usb.UsbDevice;

public class MainActivity extends AppCompatActivity implements ArduinoListener {
    Arduino arduino;
    Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sw = findViewById(R.id.led_switch);
        sw.setEnabled(false);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String status;
                if (isChecked) {
                    status = "On";
                    arduino.send("1".getBytes());
                } else {
                    status = "Off";
                    arduino.send("0".getBytes());
                }
                showMessage("Switched  " + status);
            }
        });

        arduino = new Arduino(this);
    }

    private void showMessage(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        arduino.setArduinoListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        arduino.unsetArduinoListener();
        arduino.close();
    }

    @Override
    public void onArduinoAttached(UsbDevice device) {
        arduino.open(device);
        showMessage("Arduino attached");
    }

    @Override
    public void onArduinoDetached() {
        showMessage("Arduino detached");
        sw.setEnabled(false);
    }

    @Override
    public void onArduinoMessage(byte[] bytes) {

    }

    @Override
    public void onArduinoOpened() {
        sw.setEnabled(true);
    }

}
