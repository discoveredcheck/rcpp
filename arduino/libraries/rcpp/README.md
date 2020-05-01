## Communication Protocol

Serial interface on the arduino is limited to a stream of 8-bit characters. Here we propose a protocol to expose the entire functionality of the remote app over a connection serial. Commands and their arguments will be encoded as a short string of characters. Since we only receive a single character per `loop()`, there needs to be a state machine tracking the previously received characters. There are 3 main states the application can be in

- IDLE - application is waiting for a meaningful command.
- SET - user requests to set some parameters of the application.
- GET - user requests to read some parameters of the application.

The application can only go into SET or GET states from an IDLE state. Any other combination leads to the application falling back into the IDLE state. Strict rules such as this preserve the application from inadvertently sent commands or a noisy connection.

### States

The `IDLE` state is used only for error control and does not generate any actions.

Once in the `SET` or `GET` state, the application goes into a collection mode and stores the rest of the characters in a buffer until a special end-of-command character is received. This buffered string now holds the arguments of the `SET` command, e.g., Motor speed/direction. The characters 'i', 's' and 'g' are reserved for sending the application into the `IDLE`, `SET` and `GET` states respectively.

The adapter does not understand the commands themselves. It will pass on the command strings to the caller who is supposed to handle them. See below for a description of the commands.

### Commands

The general structure of a `GET` command is `<resource><resource_num><property>` and that of the `SET` command is `<resource><resource_num><property><value>`. For instance, motors are denoted by the character 'm'. `resource_num` is either '0' or '1' for the two motors. `property` is either 'd' (for direction) or 's' for speed. Motor speeds are denote as an integer between 0 and 100.

For example, to set the speed of motor 1 to 75% send the string

`sm1s75`

and to get the direction of motor 0 send the string

`gm0d`

over serial and wait for the response string.