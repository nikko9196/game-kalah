# KalahStandardStarter

The starter kit for the "load-save-new" version of Kalah.

**Note** This repo is set up to run a continuous integration (CI) workflow on
any **push to the `submission` branch** only (not to any other branch). It is
*highly* recommended that you do your development on a different branch
(e.g. `main`) and run the tests locally (using the `Makefile` or your
IDE). When you want to "submit" and confirm that your tests still pass on the
CI workflow, you merge your development branch with the `submission` branch.

## README Contents

1. Functional requirements
2. Repository Contents
3. Test Infrastructure
4. Makefile

## Functional Requirements

The functional requirements for the game are the same as for Assignment 1. There is, however, a change in the format of the prompt. This is needed to support the new features required. The prompt now looks like this:
<pre>
Player P1
    (1-6) - house number for move
    n - New game
    s - Save game
    l - Load game
    q - Quit
Choice:
</pre>
The new features do not affect the rules or presentation of the game.

As has been the case in the past, the details of how the implementation should
behave are given in the test specification files.

### New Game

At any point, either player can restart the game. When this happens, it is as if nothing has happened before this point. So it doesn't matter which player restarts it, P1 will have the first move, and no saves (see below) have taken place.

### Load/Save Game

At any point, either player can save the current state of the game. This will record the state of the board and whose turn it is. At some later point, either player can load the saved game. The game will then proceed from that point. The game can be saved multiple times, but the load will only restore from the most recent save. If a player restarts the game, any saved game is lost.

## Repository Contents
1. `A3_Report.xlsx` - Template for assignment report.
2. `Makefile` - build script for `make`. Supports building implementation and running tests (see below)
3. `README.md` - this file (markdown) 
4. `resources` - directory containing:
    * `IO.html` - documentation for `IO` interface in test infrastructure
    * `kalah-compsci701-a3-20210910.jar` - `jar` file containing infrastructure, including test class and test specifications. Has to be on the classpath for compiling and testing
    * `test-ST-6-4-SK-H-H-SH-LSN` - directory containing the specifications for the tests for the original (horizontal, 2 human players) variant, as well as the load-save-new features. This is what is in the jar file, but has been unpacked for easy access.
    *  `junit-3.8.2.jar` - `jar` file for 3.8 JUnit. Has to be on classpath for testing.
5. `src/kalah` - directory containing:
    * `Kalah.java` - Stub class for Kalah set up to use test infrastructure. The CI will perform all of the testing by calling the `void play(IO)` method so this is what you need to implement.

## Test Infrastructure

The test infrastructure is the same as for previous assignments.

## Makefile

The makefile is the same as for previous assignments.
