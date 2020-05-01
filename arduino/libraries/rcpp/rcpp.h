#include <Arduino.h>

char read_char(bool do_echo) {
	if (Serial.available()) {
		char incoming = (char)Serial.read();
		if (do_echo)
		{
			Serial.print("Recieved ");
			Serial.println(incoming);
		}
		return incoming;
	}
	return NULL;
}