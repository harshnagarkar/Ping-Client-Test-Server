import java.io.*;
import java.net.*;
import java.util.*;
/*
 * Client to send ping requests over UDP.
 */
public class PingClient{

   private static final int MAX_TIMEOUT = 1000;	  // milliseconds
   private static int serverPort = 1044;

   public static void main(String[] args) throws Exception{
      // Get command line argument.
      if (args.length != 2) {
         System.out.println("Required arguments: host, port");
         return;
      }
      InetAddress host=InetAddress.getByName(args[0]);
      int port = Integer.parseInt(args[1]);
      
      // Create a datagram socket for receiving and sending UDP packets 
      //through the port specified on the command line.
      DatagramSocket clientSocket = new DatagramSocket(port);

    
      int sequence_number = 0;
		// Processing loop.
      while (sequence_number < 10){
    	 // Timestamp in ms when we send it
    	 Date now = new Date();
         long time = now.getTime();
         
         // Create string to send, and transfer i to a Byte Array
         String msg = "PING " + sequence_number + "\r " + time + " CRLF\n";
    	 
         byte[] buff = new byte[1024];
         buff = msg.getBytes();
         
         
         
         // Create a datagram packet to send as an UDP packet.
         DatagramPacket ping = new DatagramPacket(buff, buff.length,host,serverPort);

         // Send  the Ping datagram to the server.
         clientSocket.send(ping);
         
         try {
				// Set up the timeout 1000 ms = 1 sec
        	 	clientSocket.setSoTimeout(MAX_TIMEOUT);
				// Set up an UPD packet for recieving
				DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
				// Try to receive the response from the ping
				clientSocket.receive(response);
				// timestamp for when we received the packet
				now = new Date();
				long timeReceived = now.getTime();
				// Print the packet and the delay
				printData(response, timeReceived - time);
				
			} catch (IOException e) {
				System.out.println("Timeout for packet " + sequence_number);
			}

         sequence_number ++;
      }
      clientSocket.close();
   }

   /* 
    * Print ping data to the standard output stream.
    */
   private static void printData(DatagramPacket request,long delayTime) throws Exception{
      // Obtain references to the packet's array of bytes.
      byte[] buf = request.getData();

      // Wrap the bytes in a byte array input stream, so that you can read the data as a stream of bytes.
      ByteArrayInputStream bais = new ByteArrayInputStream(buf);

      // Wrap the byte array output stream in an input stream reader,
      // so you can read the data as a stream of characters.
      InputStreamReader isr = new InputStreamReader(bais);

      // Wrap the input stream reader in a bufferred reader,
      // so you can read the character data a line at a time.
      // (A line is a sequence of chars terminated by any combination of \r and \n.) 
      BufferedReader br = new BufferedReader(isr);

      // The message data is contained in a single line, so read this line.
      String line = br.readLine();

      // Print host address and data received from it.
      System.out.println(
         "Received from " + 
         request.getAddress().getHostAddress() + 
         ": " +
         new String(line) + " Delay: " + delayTime  );
   }
}