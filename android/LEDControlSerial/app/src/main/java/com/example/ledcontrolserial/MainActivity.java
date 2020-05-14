package com.example.ledcontrolserial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;
import android.hardware.usb.UsbDevice;

public class MainActivity extends AppCompatActivity implements ArduinoListener {
    Arduino arduino;
    Switch sw;
    Switch dir_0;
    SeekBar sb_0;
    int speed_0;

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
                    arduino.send("sl0s1|".getBytes());
                } else {
                    status = "Off";
                    arduino.send("sl0s0|".getBytes());
                }
                showMessage("Switched  " + status);
            }
        });

        sb_0 = findViewById(R.id.motor_speed_0);
        sb_0.setMin(0);
        sb_0.setMax(100);
        sb_0.setEnabled(false);
        speed_0 = 0;
        sb_0.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                speed_0 = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                arduino.send(String.format("sm0s%d|", speed_0).getBytes());
            }
        });

        dir_0 = findViewById(R.id.direction_switch);
        dir_0.setEnabled(false);
        dir_0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int dir = isChecked ? 1 : 2;
                arduino.send(String.format("sm0d%d|",  dir).getBytes());
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
        sb_0.setEnabled(false);
        dir_0.setEnabled(false);
    }

    @Override
    public void onArduinoMessage(byte[] bytes) {

    }

    @Override
    public void onArduinoOpened() {
        sw.setEnabled(true);
        sb_0.setEnabled(true);
        dir_0.setEnabled(true);
    }

}
