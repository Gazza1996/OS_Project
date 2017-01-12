// Gary Mannion - G00319609 - OS Project
// 13-1-17

package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientRequest {
	// socket value
	Socket requestSocket;
	// output and input
	ObjectOutputStream out;
	ObjectInputStream in;
	// Strings
	String message = "";
	String userinput;
	String ipaddress;
	// int
	int choice,userinput2;
	// scanner
	Scanner stdin;

	ClientRequest() {
	
	}

	// methods adapted from learn-online
	void run() 
	{
		stdin = new Scanner(System.in);
		try 
		{
			// 1. creating a socket to connect to the server
			System.out.println("Please Enter IP Address: "); // will be 127.0.0.1
			ipaddress = stdin.next(); // ipaddress
			requestSocket = new Socket(ipaddress, 2010);
			System.out.println("Connected to " + ipaddress + " in port 2010");
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			// 3: Communicating with the server
			do 
			{
				try 
				{
					
					// read message from server
					message = (String) in.readObject();
					System.out.println(message);
					// send message to server
					message = stdin.next();
					sendMessage(message);
				} 
				catch (ClassNotFoundException classNot) 
				{
					System.err.println("data received in unknown format");
				}
			} while (!message.equals("bye"));
		} 
		catch (UnknownHostException unknownHost) 
		{
			System.err.println("You are trying to connect to an unknown host!");
		} 
		catch (IOException ioException) 
		{
			ioException.printStackTrace();
		} 
		finally 
		{	
			// 4: Closing connection
			try 
			{
				in.close(); // input close
				out.close(); // output close
				requestSocket.close(); // socket close
			} 
			catch (IOException ioException) 
			{
				ioException.printStackTrace();
			}
		}
	}

	// message passing method
	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush(); // flush
			System.out.println("client>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	// Main method to run CiientRequester
	public static void main(String args[]) {
		ClientRequest client = new ClientRequest();
		client.run();
	}
}
