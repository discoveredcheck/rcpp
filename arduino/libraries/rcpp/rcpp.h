#include <Arduino.h>

char IDLE_CHAR = 'i';
char SET_CHAR = 's';
char GET_CHAR = 'g';
char EOC_CHAR = '|';

enum CommState {
    IDLE,
    SET,
    GET,
};

class CommAdapter {
    public:
        CommAdapter() {
            state = IDLE;
        }
        String loop() {
            char inp = read_char(false);
            if (!inp) {
                return "";
            }

            //Update state
            if (inp == IDLE_CHAR) {
                state = IDLE;
            } else if (inp == GET_CHAR || inp == SET_CHAR) {  // SET or GET request
                if (state == IDLE) {  // Can only go from idle to set/get
                    state = inp == SET_CHAR ? SET : GET;
                } else {  // Return to idle on error
                    Serial.println("Failed IDLE -> GET");
                    state = IDLE;
                }
            }

            //Perform state dependent operations
            String cmd = "";
            return cmd;
        }
    private:
        CommState state;
};

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