# rcpp

`rcpp` stands for remote cell phone platform. It is a small robot platform. An Android phone controls an Arduino over a USB serial connection, and the Arduino drives DC motors.

## Parts

The project has three parts. The first part is an Arduino library with a text command protocol. The second part is three Arduino sketches. The third part is an Android app.

The library header is `arduino/libraries/rcpp/rcpp.h`. The functions `read_char` and `read_string` read from the serial port. The class `SerialHandler` reads a command, checks it and runs it. It sets the motor speed with PWM on the enable input, the motor direction with the two input connections, and the built-in LED.

The sketches are in the folder `arduino/sketches`. The sketch `led_serial` turns the built-in LED on with the character 1 and off with the character 0. The sketch `motor_control_h_bridge` starts one motor with the character 1 and stops it with the character 2. The sketch `protocol` runs the `SerialHandler` with the full protocol.

The Android app is in the folder `android/LEDControlSerial`. It is written in Java. It uses the library `me.aflak.libraries:arduino` for the USB serial connection. It has a switch for the LED, a slider for the motor speed from 0 to 100, and a switch for the motor direction. The controls are enabled when the Arduino is connected and opened.

The repository also holds the wiring diagram and the 3D model files of the first body.

## Hardware

The board is an Arduino Nano Every with the ATMega4809 chip. The motor driver is an L298N, and it drives DC motors. The sketches use these motor connections.

| Motor connection | Arduino pin |
|---|---|
| Enable | 3 |
| IN1 | 4 |
| IN2 | 5 |

An Android phone connects to the Arduino with a USB cable.

The wiring diagram is `docs/images/arduino_h_bridge_dc_motors.svg`. The drawing of the first design is `docs/images/rcpp_design1.svg`. The folder `model/v1` holds the STL files of the first body. These are the base, the base top, two spur gears, the lid and the pole.

## The protocol

Commands are short text strings. They go over the serial port at 9600 baud.

A SET command has the form `s<resource><number><property><value>`. A GET command has the form `g<resource><number><property>`.

The protocol has two resources and two properties.

- The resource `m` is a motor.
- The resource `l` is the LED.
- The property `s` is the speed.
- The property `d` is the direction.

Speed and direction take these values.

- Speed is a number from 0 to 100 in percent.
- Direction 0 means stop, 1 means clockwise and 2 means counterclockwise.

The character `|` ends a command.

Three examples show how the commands work.

- `sm0s75|` sets the speed of motor 0 to 75 percent.
- `sm0d1|` turns motor 0 clockwise.
- `sl0s1|` turns the LED on.

## How to run

### Arduino

1. Copy the folder `arduino/libraries/rcpp` into your Arduino libraries folder, for example `~/Documents/Arduino/libraries`.
2. Wire the L298N driver as in the wiring diagram.
3. Open `arduino/sketches/protocol/protocol.ino` in the Arduino IDE.
4. Upload the sketch to the board.
5. Open the serial monitor at 9600 baud.
6. Send `sm0d1|` and then send `sm0s50|`.

### Android

1. Open `android/LEDControlSerial` in Android Studio.
2. Build the app and install it on the phone.
3. Connect the phone to the Arduino with a USB cable.
4. Allow the USB access.

## Limits

- The handler drives one motor. The protocol has a motor number, but the handler does not use it yet.
- GET commands are checked, but they are not answered yet.
- A speed of 0 is ignored. Stop the motor with direction 0.
- The app does not show replies from the Arduino.
- The repository has no tests.

## Plans

The plans for the project are in `ROADMAP.md`.

## License

The project is under the MIT license. See the `LICENSE` file.
