# Roadmap

I write this plan for the next six months of work on the project. It is a plan for one person, with one milestone for each month.

## Where the project is today

The project has an Arduino library that reads text commands from the serial port. The library sets the speed and the direction of one DC motor and the built-in LED. The Android app sends these commands over a USB cable. The app has a switch for the LED, a slider for the speed and a switch for the direction. The repository holds the wiring diagram and the STL files of the first body.

Some points are still open. The project drives one motor only. The Arduino does not answer GET commands. The app shows no replies. There is no control over a network yet. There are no tests.

## Month 1. Finish the protocol on the Arduino

At the end of this month, the library does all that the protocol document describes.

- Drive two motors from the library. Today the handler ignores the motor number.
- Answer GET commands with the current speed and direction. Today the handler checks them and returns nothing.
- Accept a speed of `0`. Today the handler ignores it.
- Reply to each command with an `OK` or an error code.
- Add a servo as a new resource in the protocol.
- Move the command parser into plain C++ code that also compiles on a computer. Add unit tests for it.
- Add `library.properties` so that the Arduino IDE installs the library. Today the folder is copied by hand.
- Add continuous integration that compiles the sketches and runs the tests on each push.

## Month 2. The Android app

At the end of this month, the app drives a robot with two motors and shows what the Arduino answers.

- Rename the app. Today it is called `LEDControlSerial` from the first test.
- Add a drive control with one stick that sets the speed and the direction of both motors.
- Show the replies of the Arduino. Today the method `onArduinoMessage` is empty.
- Show the connection state and an error when the cable is removed.
- Update the Gradle version and the target Android version.
- Add tests for the code that builds the command strings.

## Month 3. Control over the network

At the end of this month, I drive the robot from a second device while the phone sits on the robot.

- Add a small server in the app that takes drive commands over the home network and passes them to the Arduino.
- Add a web page with the drive control. A laptop or a second phone can drive the robot from a browser.
- Stop the motors on the Arduino when no command arrives within a set time.
- Add a setting for the port and a pairing code.

## Month 4. Sensors and feedback

At the end of this month, the robot reports its state and keeps a set speed.

- Read the battery voltage on the Arduino and add it to the protocol.
- Add wheel encoders and report the wheel speed.
- Add a speed control loop with a PID controller on the Arduino.
- Send the heading from the phone sensors to the control page.

## Month 5. Camera and driving aids

At the end of this month, I can drive the robot without a direct view of it.

- Stream the phone camera to the control page.
- Add an ultrasonic distance sensor and stop the robot in front of an obstacle.
- Add a routine that drives a straight line with the heading from the phone.
- Record a drive with its commands and sensor values to a file.

## Month 6. Body version 2, documents and release

At the end of this month, another person can build the robot from the repository.

- Design the second body with mounts for the phone, the battery and the sensors. Add the source CAD files next to the STL files.
- Write the parts list, the wiring guide and the assembly guide.
- Write the protocol reference with every command and reply.
- Tag version 1.0 of the library and the app.

## Out of scope

The project has no machine learning, and the driving aids use fixed rules and a PID controller. The project has no data of other people.
