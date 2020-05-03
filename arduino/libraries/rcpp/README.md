# Communication Protocol

The serial interface on arduino is limited to reading/writing a stream of 8-bit characters. We propose a protocol to expose the entire functionality of the remote app over a this connection. Commands and their arguments will be encoded as short strings of characters.

## Commands

There are two main sets of supported commands - the `SET` and the `GET` commands. The general structure of a `GET` command is `g<resource><resource_num><property>` and that of the `SET` command is `s<resource><resource_num><property><value>`. Accepted values for each part of the command are

- `resource` = {`m`} denoting the motor resource type.
- `resource_num` = {`0`, `1`} denoting the two motors.
- `property` = {`s`, `d`} denoting speed and direction respectively.
- `value` = [`0`, `9`] denoting values for motor speed and others as required.

You may add the `|` character denoting end of command for faster interpretation on the arduino. Otherwise the serial adapter waits a specified amount of time before terminating a string of characters.

For example, to set the speed of motor 1 to 75% send the string

`sm1s75|`

To get the current direction of motion for motor 0 send the string

`gm0d|`

The result will be returned as a string over the same serial connection.
