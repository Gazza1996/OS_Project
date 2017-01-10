package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Server{
	ServerSocket providerSocket;
	Socket connection = null;

	ObjectOutputStream out;
	ObjectInputStream in;
	//Scanner input;
	String message,message2;
	//String user_input;
	Account temp;
	int choice;
	Scanner stdin;
	boolean flag=true;
	
	ArrayList<Account> list = new ArrayList<Account>();
	
	
	Server()
	{
		//input = new Scanner(System.in);
	}
	void listener()
	{
		try{
			//1. creating a server socket
			providerSocket = new ServerSocket(2010, 10);
			//2. Wait for connection
			System.out.println("Waiting for connection");
			
			connection = providerSocket.accept();
			
			System.out.println("Connection received from " + connection.getInetAddress().getHostName());
			//3. get Input and Output streams
			out = new ObjectOutputStream(connection.getOutputStream());
			out.flush();
			in = new ObjectInputStream(connection.getInputStream());
			
			//4. The two parts communicate via the input and output streams
			do{
				try{
					sendMessage("Please enter 1 to register... 2 for exisiting account... exit to quit");	
					message = (String)in.readObject();
					choice = new Integer(message);
					
					if(choice==1)
					{
						sendMessage("Please enter Name");
						message = (String)in.readObject();
						String name = message;
						
						sendMessage("Please enter Address");
						message = (String)in.readObject();
						String address = message;
						
						sendMessage("Please enter A/C number");
						message = (String)in.readObject();
						int accNum = new Integer(message);
						
						sendMessage("Please enter Username");
						message = (String)in.readObject();
						String username = message;
						
						sendMessage("Please enter Password");
						message = (String)in.readObject();
						String password = message;
						
						int balance=0;
						
						list.add(new Account(name, address, accNum, username, password, balance));
						
						sendMessage("Account: "+list);
					}
					
					else if(choice==2)
					{
							sendMessage("Please enter Username");
							message = (String)in.readObject();
							System.out.println(message);
							
							sendMessage("Please enter Password");
							message2 = (String)in.readObject();
							System.out.println(message2);
					}
					
					for(int i=0; i<list.size();i++)
					{
						if(list.get(i).getUsername().equals(message) && list.get(i).getPassword().equals(message2))
						{
							temp=list.get(i);
							flag=true;
							sendMessage("Welcome " + temp.getName() + "\nAccount details " + temp);
						}
						if(list.get(i).getUsername()!=(message) && list.get(i).getPassword()!=(message2))
						{
							sendMessage("Authentication un-succesful");
						}
						else
						{
							sendMessage("not working");
						}
					}
					
				}
				catch(ClassNotFoundException classnot){
					System.err.println("Data received in unknown format");
				}
			}while(!message.equals("Thank You!"));
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
		finally{
			//4: Closing connection
			try{
				in.close();
				out.close();
				providerSocket.close();
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
			System.out.println("server>" + msg);
		}
		catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		Server server = new Server();
		while(true)
		{
			server.listener();
		}
	}
}
