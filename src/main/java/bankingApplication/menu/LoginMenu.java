package bankingApplication.menu;

import bankingApplication.dao.UserDao;
import bankingApplication.model.JointAccount;
import bankingApplication.model.SingleAccount;
import bankingApplication.transactions.Transactions;
import bankingApplication.util.ScannerUtil;

public class LoginMenu implements Menu {

	UserDao dao = new UserDao();
	Transactions tr = new Transactions();
	
	void print(){
		System.out.println("--transaction menu--- ");
		System.out.println("press 0. close account");//---
		System.out.println("press 1. deposit");//done
		System.out.println("press 2. withdrawal");//done
		System.out.println("press 3. view balance");//done
		System.out.println("press 4. transfer money");//done
		System.out.println("press 5. Log Out");
	}
	
	public Menu process() {
		// TODO Auto-generated method stub
		
		System.out.println("in login menu");
		
		System.out.println("Enter username: ");
		String username = ScannerUtil.getString();
		System.out.println("Enter password: ");
		String password = ScannerUtil.getString();
		
		boolean isValid;
		
		if(isValid = dao.validateLogin(username, password)) {
			System.out.println("login successful ");
			String str = dao.getAccountType(username, password);
			
			String single = "single";
			String joint = "joint";
			
			if(str.contains(single)) {
				System.out.println("int a single account");
				SingleAccount singleAccount = dao.userSingleAccount(username, password);
				singleAccountTransactions(singleAccount);
				
			}else if (str.contains(joint)) {
				System.out.println("in a joint account");
				JointAccount jointAccount = dao.userJointAccount(username, password);
				jointAccountTransactions(jointAccount);
				
			}else {
				return new MainMenu();
			}
			
			
		}else {
			System.out.println("login failed");
		}
		
		return new MainMenu();
		
	}
	
	private void jointAccountTransactions(JointAccount ac) {
		// TODO Auto-generated method stub
		
		int n = 5;
		do {
			
			print();			
			n = ScannerUtil.getInput(5);
			
			switch(n) {
			
			case 0: tr.close(ac); break;
			case 1: tr.deposit(ac); break;
			case 2: tr.withdraw(ac); break;
			case 3: dao.viewBalance(ac); break;
			case 4: tr.transfer(ac); break;
			case 5: new MainMenu();
			default: new MainMenu();
			}
		}while(n < 5);		
	}
	
	private void singleAccountTransactions(SingleAccount ac) {
		// TODO Auto-generated method stub
		int n = 5;
		do {
			
			print();			
			n = ScannerUtil.getInput(5);
			
			switch(n) {
			
			case 0: tr.close(ac); break;
			case 1: tr.deposit(ac); break;
			case 2: tr.withdraw(ac); break;
			case 3: dao.viewBalance(ac); break;
			case 4: tr.transfer(ac); break;
			case 5: new MainMenu();
			default: new MainMenu();
			}
		}while(n < 5);		
				
	}
	
	
	

}














