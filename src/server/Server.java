// Gary Mannion - G00319609 - OS Project
// 13-1-17

package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server {
	// Main Method.. adapted from learn-online
	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ServerSocket m_ServerSocket = new ServerSocket(2010, 10); // socket 
		// variable for id
		int id = 0;
		// while loop for socket acceptance
		while (true) {
			Socket clientSocket = m_ServerSocket.accept();
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
			cliThread.start(); // start function for thread
		}
	}
}
// adapted from learn-online
class ClientServiceThread extends Thread 
{
	// socket
	Socket clientSocket;
	// String
	String message, message2, logName;
	// int
	int clientID = -1;
	int choice,choice2, withdraw, lodgement;
	int log;
	// boolean
	boolean flag = false;
	// output and input
	ObjectOutputStream out;
	ObjectInputStream in;
	// random function
	Random rand = new Random();
	// account array list
	ArrayList<Account> list = new ArrayList<Account>();
	
	// Constructor.. adapted from learn-online
 	ClientServiceThread(Socket cs, int i) 
	{
		clientSocket = cs;
		clientID = i;
	}

 	// message passing
	void sendMessage(String msg) 
	{
		try 
		{
			out.writeObject(msg); // write the object
			out.flush(); // flush
			System.out.println("server> " + msg); // print to console
		} 
		catch (IOException ioException) // error exception
		{
			ioException.printStackTrace();
		}
	}
	
	// Method used for the choices that user send to the server after server sends first message to client
	public void UserInfo() throws ClassNotFoundException, IOException{
		
		if(choice==1)
		{
			addAccount();
		}
		else if(choice==2)
		{
			login();
		}
/*		else if(choice==3)
		{
			Deposit();
		}*/
		else if(choice==4)
		{
			withdraw();
		}
	}
	
	// Method used for registering Account
	public void addAccount() throws ClassNotFoundException, IOException {
		// member name
		sendMessage("Please enter Name");
		message = (String)in.readObject();
		String name = message;
		// address
		sendMessage("Please enter Address");
		message = (String)in.readObject();
		String address = message;
		// account number is randomised.. adapted from a previous project
		int accNum = rand.nextInt(60) + 1;
		for(Account a:list){
			while(a.getAccNum() == accNum)
			{
				accNum = rand.nextInt(60) + 1;
			}
			break;
		}
		// user-name
		sendMessage("Please enter Username");
		message = (String)in.readObject();
		String username = message;
		// password
		sendMessage("Please enter Password");
		message = (String)in.readObject();
		String password = message;
		// set account balance to 0
		int balance=0;
		// Array List for inputting details for member
		list.add(new Account(name, address, accNum, username, password, balance));
		
		System.out.println("\n\n"+list);
	}
	
	// login method
	public void login() throws ClassNotFoundException, IOException {
		// user-name login
		sendMessage("Please enter Username");
		message = (String)in.readObject();
		System.out.println(message);
		// password login
		sendMessage("Please enter Password");
		message2 = (String)in.readObject();
		System.out.println(message2);
		// Account details.. ArrayList
		for(Account l:list)
		{
			// if details entered are correct
			if(l.getUsername().equals(message) && l.getPassword().equals(message2))
			{
				// set variables for when login is correct what to return
				logName = l.getName();
				log = l.getAccNum();
				flag = true;
				break;
			}
		}
	}
	
	// when member login is correct 
	public void loginCorrect() throws ClassNotFoundException, IOException {
		
			// menu when login is correct
			sendMessage("\nWelcome "+logName+"\n\nPlease enter 1 to update member details" + 
												"\n2 return to main menu" +
												"\n3 to lodgement" + 
												"\n4 to withdraw" +
												"\n5 view last 10 transactions");
			message=(String)in.readObject(); // read message
			choice2 = new Integer(message); // second choice variable created
			
			// member update
			if(choice2 == 1)
			{
				memberUpdate();
			}
			// return to main menu
			else if(choice2 == 2){
				flag = false; // set flag to false to return
			}
			// deposit
			else if(choice2 == 3)
			{
				lodgement();
			}
			// withdraw
			else if(choice2 == 4)
			{
				withdraw();
			}
			// last transactions
			else if(choice2 == 5)
			{
				transactions();
			}
	}
	
	public void memberUpdate() {
		// update member details
		
	}
	
	// deposit from account
	public void lodgement() throws ClassNotFoundException, IOException{
		int balance = 0; // set balance to 0
		sendMessage("Lodgement amount: "); // send message
		message = (String) in.readObject(); // read message
		lodgement = new Integer(message); // set deposit variable
		
		// loop through ArrayList
		for (Account a : list) 
		{
			// similar to withdraw method.. matches number
			if (a.getAccNum() == log)
			{	// sets deposit amount to account balance
				balance = a.getBalance(); // should get current balance
				balance += lodgement; // adds deposit amount to balance
				a.setBalance(balance); // set the account balance from array list
				System.out.println(balance);
				sendMessage("Your account balance now is: " + balance); // prints to screen
				break;
			}
		}
	}
	
	// withdraw from account
	public void withdraw() throws ClassNotFoundException, IOException{
		int balance = 0;
		sendMessage("withdrawal amount: ");	
		message = (String) in.readObject();
		withdraw = new Integer(message);
		
		// loop through ArrayList
		for (Account a : list) 
		{
			// if account number is correct
			if (a.getAccNum() == log)
			{
				
				balance = a.getBalance();
				balance -= withdraw;
				
				// if statement for if credit limit is exceeded
				if(balance < -1000)
				{
					System.out.println(balance);
					sendMessage("insuficent funds :"+balance+" your limit is €1000");	
				}
				else
				{// sets account balance after amount is entered
					a.setBalance(balance);
					System.out.println(balance);
					sendMessage("Your account balance now is:" + balance);
				}	
			}
		}
	}
	
	public void transactions(){
		// view last 10 transactions
	}
	
	// run method adapted from learn-online
	public void run() 
	{
		System.out.println("Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
		try 
		{
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());

			do 
			{
				try 
				{	// 
					sendMessage("Please enter 1 for new user" + 
								"\n2 for returning user" + 
								"\nexit to leave application");
					// read message 
					message = (String) in.readObject();
					choice = new Integer(message); // returns choice selected
					UserInfo();// user-info method
					
					if(flag == true) // if flag is equal to true 
					{
							do // function for flag equal to true
							{
								loginCorrect(); // method name
								
							}
							while(flag == true);
					}
				}	
				// adapted from learn-online
				catch (ClassNotFoundException classnot) 
				{
					System.err.println("Data received in unknown format");
				}
				// adapted from learn-online
			} while (!message.equals("exit")); // exit returns
			System.out.println(
					"Ending Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName()); // closes sockets and host
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}	
}// end of program