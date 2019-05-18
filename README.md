# Ping-Client-Test-Server
Java Implementation of Ping Client and Test Server

The has a sample Ping client which send a ping request 10 times and upon arrival counts the Round Trip Time.
There is also a test Sevrer in the same project which we can use to test. But as we need to simulate the natural network congession and package drops due to buffer overflow.
There is a random generator coded into it which drops packets.

In order to run the program we need to commandline arguments of port and ip. 
As the files shown above are eclipse. In eclipes run the 2 client server program seperately and add additional commandline arguments in the run configuration window.
