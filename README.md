# Conway's Game of Life

Please see this link for context onboarding: https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life

## Requirements

- JDK 11 or above
- Maven 2.4 or above
- Junit 4.13.2 or above

## How to run

1. Build
   ```
   ./bin/setup
   ```
   
2. Run program with file input (delay between state is 500 milliseconds as default)
   ```
   ./bin/run <input file name>
   ```
   ```
   ./bin/run input/blinker.txt
   ./bin/run input/pulsar.txt
   ./bin/run input/glider.txt
   ./bin/run input/gosper_glider_gun.txt
   ./bin/run input/glider_destroying_cthulhu.txt
   ```
   Type cmd + c to exit
   
3. Run program with custom delay
   ```
   ./bin/run <input file name> <delay in milliseconds>
   ```
   ```
   ./bin/run input/blinker.txt 100
   ./bin/run input/pulsar.txt 100
   ./bin/run input/glider.txt 100
   ./bin/run input/gosper_glider_gun.txt 100
   ./bin/run input/glider_destroying_cthulhu.txt 100
   ```
   Type cmd + c to exit
   