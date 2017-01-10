package client;

public class Account {
	public String name;
	public String address;
	public int accNum;
	public String username;
	public String password;
	public int balance;
	
	public Account() {

	}

	public Account(String name, String address, int accNum, String username, String password, int balance) {
		this.name = name;
		this.address = address;
		this.accNum = accNum;
		this.username = username;
		this.password = password;
		this.balance=balance;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public int getAccNum() {
		return accNum;
	}

	public void setAccNum(int accNum) {
		this.accNum = accNum;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Account [name=" + name + ", address=" + address + ", accNum=" + accNum + ", username=" + username
				+ ", password=" + password + ", balance=" + balance + "]";
	}
}