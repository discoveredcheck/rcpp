To use the code under this folder, it must be first installed within Arduino standard library location (yes, its ugly!).

### Installation
To install, simply copy the contents of this folder under the standard arduino libraries path. Make sure to include the parent folder of each library (e.g., `rcpp/rcpp.h`).

Arduino libraries path is usually a location similar to `~/Documents/Arduino/libraries` on Mac and Linux. For detailed information check [here](https://www.arduino.cc/en/hacking/libraries)

### Usage
After copying the folder, add

`#include <rcpp.h>`

at the top of your .ino file to get all the code from `rcpp.h` into your sketch.
