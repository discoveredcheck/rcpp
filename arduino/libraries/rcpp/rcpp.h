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

String read_string(bool do_echo, char end_char) {
    if (Serial.available()) {
        String incoming = (String)Serial.readStringUntil(end_char);
        if (do_echo) {
            Serial.print("Recieved ");
			Serial.println(incoming);
        }
        return incoming;
    }
    return "";
}

class SerialHandler {
    public:
        void setup(int baud_rate, int motor_0[3]) {
            Serial.begin(baud_rate);
            EN_0 =  motor_0[0];
            IN1_0 =  motor_0[1];
            IN2_0 =  motor_0[2];
            for(int i=0; i<3; i++) {
                pinMode(motor_0[i], OUTPUT);
            }
            digitalWrite(EN_0, LOW);
            digitalWrite(IN1_0, LOW);
            digitalWrite(IN2_0, LOW);
        }
        String loop() {
            String cmd = read_string(false, EOC_CHAR);
            if (cmd != "") {
                if (validate(cmd)) {
                    Serial.print("CMD: ");
                    Serial.println(cmd);
                    return handle(cmd);
                } else {
                    Serial.print("Invalid: ");
                    Serial.println(cmd);
                }
            }
            return "";
        }
    private:
        int EN_0, IN1_0, IN2_0;
        char IDLE_CHAR = 'i';
        char GET_CHAR = 'g';
        char SET_CHAR = 's';
        char EOC_CHAR = '|';
        String RESOURCE_CHARS = "m";
        String RESOURCE_NUM_CHARS = "0123456789";
        String PROPERTY_CHARS = "ds";
        String VALUE_CHARS = "0123456789";

        bool validate(String cmd) {
            if (!string_contains("gs", cmd.charAt(0))) {
                return false;
            }
            bool is_get = cmd.charAt(0) == 'g';
            if (!string_contains(RESOURCE_CHARS, cmd.charAt(1))) {
                return false;
            }
            if (!string_contains(RESOURCE_NUM_CHARS, cmd.charAt(2))) {
                return false;
            }
            if (!string_contains(PROPERTY_CHARS, cmd.charAt(3))) {
                return false;
            }
            if (is_get) {
                if (4 != cmd.length()) {
                    return false;
                }
            } else {
                int i;
                for(i=4; i<cmd.length(); i++) {
                    if(!string_contains(VALUE_CHARS, cmd.charAt(i))) {
                        return false;
                    }
                }
                if (i != cmd.length()) {
                    return false;
                }
            }
            return true;
        }
        String handle(String cmd) {
            if (cmd.charAt(0) == 's') {
                if (cmd.charAt(1) == 'm') {
                    if (cmd.charAt(3) == 's') {
                        int pin = EN_0;
                        int val = cmd.substring(4).toFloat();
                        if (0 < val && val <= 100) {
                            analogWrite(pin, (int)(val*255./100.));
                        }
                    } else if(cmd.charAt(3) == 'd') {
                        int pin_in1 = IN1_0;
                        int pin_in2 = IN2_0;
                        switch(cmd.charAt(4)) {
                            case '0':
                                digitalWrite(pin_in1, LOW);
                                digitalWrite(pin_in2, LOW);
                                break;
                            case '1':
                                digitalWrite(pin_in1, HIGH);
                                digitalWrite(pin_in2, LOW);
                                break;
                            case '2':
                                digitalWrite(pin_in1, LOW);
                                digitalWrite(pin_in2, HIGH);
                                break;
                        }
                    }
                }
            }
            return "";
        }
        bool string_contains(String parent, char c) {
            for(int i=0; i<parent.length(); i++) {
                if (c == parent.charAt(i)) {
                    return true;
                }
            }
            return false;
        }
};
