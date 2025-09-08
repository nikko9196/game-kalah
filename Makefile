# The Github continuous integration actions for this course use this Makefile,
# so DO NOT CHANGE IT UNDER ANY CIRCUMSTANCES. If the CI process fails because
# you changed this file, it is on you.

# If you need to change something (e.g. to make this work on Windows),
# COPY this file and change and use the copy.

# Note that the syntax used here is typical for most forms of Unix. For
# Windows, at minimum "/" has to be replaced by "\" and ":" (colon) has to 
# be replaced by ";" (semicolon). Other changes may be necessary.

KALAHJAR=kalah-compsci701-a3-20210910.jar
TESTCLASS=kalah.test.TestKalahLSN

tests: compile
	java -cp resources/junit-3.8.2.jar:resources/${KALAHJAR}:bin junit.textui.TestRunner ${TESTCLASS}
play: compile
	java -cp resources/junit-3.8.2.jar:resources/${KALAHJAR}:bin kalah.Kalah
  
compile: clean
	mkdir -p bin
	javac -d bin -encoding utf8 -cp resources/junit-3.8.2.jar:resources/${KALAHJAR}:src src/kalah/Kalah.java

clean:
	/bin/rm -rf bin
