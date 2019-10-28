package bankingApplication.menu;

import java.util.ArrayList;
import java.util.List;

import bankingApplication.dao.UserDao;
import bankingApplication.model.JointAccount;
import bankingApplication.model.SingleAccount;
import bankingApplication.util.ScannerUtil;

public class CreateAccountMenu implements Menu {

	void print() {
		System.out.println("1: for single account");
		System.out.println("2: for joint account");
		System.out.println("0: exit");
	}
	
	private UserDao dao = new UserDao();
	public Menu process() {

		// TODO Auto-generated method stub
		print();
		
		int n = ScannerUtil.getInput(2);
		
		if (n == 1 ) {
			System.out.println("in createSingle");
			createSingle();
		}else if (n == 2) {
			System.out.println("in createJoint");
			createJoint();
		}
		return new MainMenu();
	}
	
	
	private void createJoint() {
		// TODO Auto-generated method stub
		
		List<JointAccount> jointUsers = new ArrayList<JointAccount>();
		
		System.out.println("Enter number of user:");
		int num = ScannerUtil.getInput();
		for (int i = 0; i < num; i++)
		{
			System.out.println("User " + (i+1) +" name: ");
			String name = ScannerUtil.getString();
			
			System.out.println("User " + (i+1) +" username: ");
			String userName = ScannerUtil.getString();
			
			System.out.println("User " + (i+1) +" password: ");
			String password = ScannerUtil.getString();
			
			jointUsers.add(new JointAccount(name, userName, password));

		}
		
		System.out.println("Enter total balance for Joint Account ");
		double balance = ScannerUtil.getBalance();		
				
		dao.persistJoint(jointUsers, balance, "joint");
	}
	private void createSingle() {
		// TODO Auto-generated method stub
		
		System.out.println("Enter name: ");
		String name = ScannerUtil.getString();
		
		System.out.println("Enter UserName: ");
		String userName = ScannerUtil.getString();
		
		System.out.println("Enter password: ");
		String password = ScannerUtil.getString();
		
		System.out.println("Enter a balance: ");
		double balance = ScannerUtil.getBalance();
		
		SingleAccount singleUser = new SingleAccount(name, userName, password, balance, "single");
		
		dao.persistSingle(singleUser);
		
	}

}
