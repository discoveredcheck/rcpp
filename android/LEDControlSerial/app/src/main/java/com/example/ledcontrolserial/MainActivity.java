package com.example.ledcontrolserial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;
import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;
import android.hardware.usb.UsbDevice;

import com.goodiebag.protractorview.ProtractorView;

public class MainActivity extends AppCompatActivity implements ArduinoListener {
    Arduino arduino;
    Switch sw;
    ProtractorView servo_0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sw = findViewById(R.id.led_switch);
        sw.setEnabled(false);
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String status;
            if (isChecked) {
                status = "On";
                arduino.send("sl0s1|".getBytes());
            } else {
                status = "Off";
                arduino.send("sl0s0|".getBytes());
            }
            showMessage("Switched  " + status);
        });
        arduino = new Arduino(this);
        servo_0 = findViewById(R.id.servo_0);
        servo_0.setEnabled(false);
        servo_0.setOnProtractorViewChangeListener(new ProtractorView.OnProtractorViewChangeListener() {
            @Override
            public void onProgressChanged(ProtractorView pv, int progress, boolean b) {
                arduino.send(String.format("sm0p%d|", pv.getAngle()).getBytes());
            }

            @Override
            public void onStartTrackingTouch(ProtractorView pv) {

            }

            @Override
            public void onStopTrackingTouch(ProtractorView pv) {

            }
        });
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
        servo_0.setEnabled(false);
    }

    @Override
    public void onArduinoMessage(byte[] bytes) {

    }

    @Override
    public void onArduinoOpened() {
        sw.setEnabled(true);
        servo_0.setEnabled(true);
    }

}
