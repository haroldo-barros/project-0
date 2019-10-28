package bankingApplication.model;

public class SingleAccount {
	
	private String name;
	private String username;
	private String password;
	private int accountID;
	private double balance;
	private String accountType;
	
	
	
	
	public SingleAccount(String name, String username, String password, int accountID, double balance,
			String accountType) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.accountID = accountID;
		this.balance = balance;
		this.accountType = accountType;
	}

	public SingleAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SingleAccount(String name, String username, String password, double balance,
			String accountType) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.balance = balance;
		this.accountType = accountType;
	}
	
	
	
	@Override
	public String toString() {
		return "SingleAccount [name=" + name + ", username=" + username + ", password=" + password + ", accountID="
				+ accountID + ", Balance=" + balance + ", accountType=" + accountType + "]";
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

}
