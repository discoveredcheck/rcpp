#include <rcpp.h>

// Motor A connections
int enA = 3;
int in1 = 4;
int in2 = 5;

bool IS_MOTOR_A_ON = false;
const char *serial_msg = NULL;

void setup() {
  // Set all the motor control pins to outputs
  Serial.begin(9600);
  serial_msg = { "Starting rcpp\n"};
  Serial.print(serial_msg);

  pinMode(enA, OUTPUT);
  pinMode(in1, OUTPUT);
  pinMode(in2, OUTPUT);

  // Turn off motors - Initial state
  digitalWrite(in1, LOW);
  digitalWrite(in2, LOW);

}

void loop() {
   char input = read_char(true);
   if(input == '1')
   {
      IS_MOTOR_A_ON = true;
   }if(input == '2'){
      IS_MOTOR_A_ON = false;
   }

  update_motor_a(IS_MOTOR_A_ON);

}

void update_motor_a(bool is_active){
  if(is_active){
    analogWrite(enA, 100);
    // Turn on motor A
    digitalWrite(in1, HIGH);
    digitalWrite(in2, LOW);
  }else{
    digitalWrite(in1, LOW);
    digitalWrite(in2, LOW);
  }
}
