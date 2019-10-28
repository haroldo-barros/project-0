package bankingApplication.transactions;

import java.util.Scanner;

import bankingApplication.dao.UserDao;
import bankingApplication.menu.MainMenu;
import bankingApplication.model.JointAccount;
import bankingApplication.model.SingleAccount;
import bankingApplication.util.ScannerUtil;

public class Transactions{

	UserDao dao = new UserDao();
	
	public void deposit(SingleAccount ac) {
		System.out.println("in deposit");
		double amount = ScannerUtil.getDeposit();
		
		ac.setBalance(ac.getBalance() + amount); 
		
		dao.updateData(ac);
	}
	
	public void deposit(JointAccount ac) {
		System.out.println("in deposit");
		double amount = ScannerUtil.getDeposit();
		
		ac.setBalance(ac.getBalance() + amount); 
		
		dao.updateData(ac);
	}
	
	public void withdraw(SingleAccount ac) {
		System.out.println("in withdrawal");
		double amount = ScannerUtil.getWithdrawal(ac.getBalance());
		
		ac.setBalance(ac.getBalance() - amount); 
		
		dao.updateData(ac);
	}
	
	public void withdraw(JointAccount ac) {
		System.out.println("in withdrawal");
		double amount = ScannerUtil.getWithdrawal(ac.getBalance());
		
		ac.setBalance(ac.getBalance() - amount); 
		
		dao.updateData(ac);
	}

	public void viewBalance(SingleAccount ac) {
		dao.viewBalance(ac);
	}
	
	public void viewBalance(JointAccount ac) {
		dao.viewBalance(ac);
	}
	
	public void transfer(SingleAccount ac) {
		
		System.out.println("Enter id to transfer money to");
		int actId = ScannerUtil.getTranferIdNum();
		
		while(dao.validateAccoutNum(actId) == false) {
			System.out.println("account doest exist /try another account");
			actId = ScannerUtil.getTranferIdNum();
		}
		
		System.out.println("Enter amount you would like to transfer");
		double amount = ScannerUtil.getWithdrawal(ac.getBalance());
		
		dao.transferMoney(ac, actId, amount);
		
		System.out.println("transfer completed");
		
	}
	
	public void transfer(JointAccount ac) {
		
		System.out.println("Enter id to transfer money to");
		int actId = ScannerUtil.getTranferIdNum();
		
		while(dao.validateAccoutNum(actId) == false) {
			System.out.println("account doest exist /try another account");
			actId = ScannerUtil.getTranferIdNum();
		}
		
		System.out.println("Enter amount you would like to transfer");
		double amount = ScannerUtil.getWithdrawal(ac.getBalance());
		
		dao.transferMoney(ac, actId, amount);
		
		System.out.println("transfer completed");
	}
	
	public void close(SingleAccount ac) {
		Scanner sn = new Scanner(System.in);
		System.out.println("Are You sure you want to delete Account");
		System.out.println("Press 1 for no\nPress 2 for yes");
		
		int n = sn.nextInt();
		
		if (n == 2) {
			
			dao.deleteAccount(ac);
			System.out.println("table deleted");
			
			
		}
	}
	
	public void close(JointAccount ac) {
		Scanner sn = new Scanner(System.in);
		System.out.println("Are You sure you want to delete Account");
		System.out.println("Press 1 for no\nPress 2 for yes");
		
		int n = sn.nextInt();
		
		if (n == 2) {
			dao.deleteAccount(ac);
			System.out.println("table deleted");
			new MainMenu();
			
		}
	}
}












