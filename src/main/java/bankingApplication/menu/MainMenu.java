package bankingApplication.menu;

import bankingApplication.util.ScannerUtil;

public class MainMenu implements Menu {

	void print() {
		System.out.println("press 1.  Login");
		System.out.println("press 2.  Create account");
		System.out.println("press 0.  Exit");
	}
	public Menu process() {
		// TODO Auto-generated method stub
		print();
		
		int n = ScannerUtil.getInput(2);
		
		switch(n) {
		
		case 0: return null;
		case 1: return new LoginMenu();
		case 2: return new CreateAccountMenu();
		default: return null;
		}
	}

}
