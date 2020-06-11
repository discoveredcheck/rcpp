#include <Arduino.h>
#include <rcpp.h>

int MOTOR_0 = 8;
int BAUD_RATE = 9600;

SerialHandlerBase* handler = new SerialHandlerServo(BAUD_RATE, MOTOR_0);

void setup() {
    handler->setup();
}

void loop() {
  handler->loop(false);
}
