#include <Arduino.h>
#include <rcpp.h>

void setup() {
	Serial.begin(9600);
	pinMode(LED_BUILTIN, OUTPUT);
}

void loop() {
	char input = read_char(true);
	if (input) {
		if (input == '1') {
			digitalWrite(LED_BUILTIN, HIGH);
		}
		else if (input == '0') {
			digitalWrite(LED_BUILTIN, LOW);
		}
	}
}
