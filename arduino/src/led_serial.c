void setup() {
	Serial.begin(57600);
	pinMode(LED_BUILTIN, OUTPUT);
}

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
