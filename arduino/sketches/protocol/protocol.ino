#include <Arduino.h>
#include <rcpp.h>

int MOTOR_0[3] = {3, 4, 5};  // EN, IN1, IN2
int BAUD_RATE = 9600;

SerialHandler handler;

void setup() {
    handler.setup(BAUD_RATE, MOTOR_0);
}

void loop() {
	handler.loop();
}
