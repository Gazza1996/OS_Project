package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
public class ClientRequest{
	Socket requestSocket;
	ObjectOutputStream out;
 	ObjectInputStream in;
 	String message,message2;
 	String userinput;
 	Scanner user_input;
 	int userinput2;
 	
 	
 	Scanner stdin;
 	
	ClientRequest()
	{
		user_input = new Scanner(System.in);
	}
	void run()
	{
		stdin = new Scanner(System.in);
		try{
			//1. creating a socket to connect to the server
			requestSocket = new Socket("127.0.0.1", 2010);
			System.out.println("Connected to localhost in port 2010");
			//2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			//3: Communicating with the server
			do{
				try{
					//if(message=="1")
					
					//read message from server (Name)
					message = (String) in.readObject();
					System.out.println(message);
					//send message to server
					message = stdin.next();
					sendMessage(message);
					//read message from server (Address)
					message = (String) in.readObject();
					System.out.println(message);			
					//send message to server
					message = stdin.next();
					sendMessage(message);
					//read message from server  (Acc num)
					message = (String) in.readObject();
					System.out.println(message);
					//send message to server
					message = stdin.next();
					sendMessage(message);
					//read message from server  (Username)
					message = (String) in.readObject();
					System.out.println(message);
					//send message to server
					message = stdin.next();
					sendMessage(message);
					//read message from server  (Password)
					message = (String) in.readObject();
					System.out.println(message);
					//send message to server
					message = stdin.next();
					sendMessage(message);
					//read message from server (Confirmation)
					message = (String) in.readObject();
					System.out.println(message);
					
					
					if(message=="2")
					{
						//read message from server (Username)
						message = (String) in.readObject();
						System.out.println(message);
						//send message to server
						message = stdin.next();
						sendMessage(message);
						
						//read message from server (Password)
						message = (String) in.readObject();
						System.out.println(message);
						//send message to server
						message = stdin.next();
						sendMessage(message);
						
						//message about (authentication)
						message = (String) in.readObject();
						System.out.println(message);
						
						//read message from server (menu2 choice)
						message = (String) in.readObject();
						System.out.println(message);
						//send message to server
						message = stdin.next();
						sendMessage(message);
						
						
					}
					
					
					message = stdin.next();
					if(message.compareToIgnoreCase("no")==0)
					{
						sendMessage("exit");
						message = (String)in.readObject();
					}
					else if(message.compareToIgnoreCase("yes")==0)
					{
						sendMessage("Continue");
					}
					
					if(message.contains("Authentication succesful"))
					{
						
						System.out.println("please choose another option"
									+ "\n1 for change details,"
									+ "\n2 for Lodgement,"
									+ "\n3 for Withdrawel,"
									+ "\n4 for View last ten transactions,"
									+ "\n5 to exit");
					}	
				}
				catch(ClassNotFoundException classNot){
					System.err.println("data received in unknown format");
				}
			}while(!message.equals("!!"));
		}
		catch(UnknownHostException unknownHost){
			System.err.println("You are trying to connect to an unknown host!");
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				requestSocket.close();
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	}
	void sendMessage(String msg)
	{
		try{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		ClientRequest client = new ClientRequest();
		client.run();
	}
	

	void addAccount(){
		
		Account account = new Account();
		// name
		System.out.println("Enter name");
		userinput=stdin.next();
		account.setName(userinput);
		// address
		System.out.println("Enter address");
		userinput=stdin.next();
		account.setAddress(userinput);
		// acc num
		System.out.println("Enter account number");
		userinput2=stdin.nextInt();
		account.setAccNum(userinput2);
		// username
		System.out.println("Enter username");
		userinput=stdin.next();
		account.setUsername(userinput);
		// password
		System.out.println("Enter password");
		userinput=stdin.next();
		account.setPassword(userinput);
		
		sendMessage(account.toString());
	}
}