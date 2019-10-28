package bankingApplication.util;

import java.util.Scanner;

public class ScannerUtil {
	static Scanner sc = new Scanner(System.in);
	
	public static int getInput(int max) {
		int input = -1;
		//int input = sc.nextInt();
		
		while (input < 0 || input > max) {
			System.out.println("enter number between 0 - " + max);
			if(!sc.hasNextInt()) {
				sc.nextLine();
				continue;
			}
			
			input = sc.nextInt();
		}
		return input;
	}
	
	public static int getInput() {
		
		//int input = sc.nextInt();
		int input = -1;
		
		while (input < 2) {
			System.out.println("At least 2 user for joint account");
			
			if(!sc.hasNextInt()) {
				sc.nextLine();
				continue;
			}
			
			input = sc.nextInt();
			}
		return input;
	}
	
	public static String getString() {
		
		String input = "";
		
		while(input.isEmpty()) {
			input = sc.nextLine();
		}
		
		return input;
	}
	
	public static double getBalance() {
		
		double input = 0;
		
		while(input < 25) {
			System.out.println("A minmum of $25 is required to open account");
			if(!sc.hasNextDouble()) {
				sc.nextLine();
				continue;
			}
			input = sc.nextDouble();			
		}
		
		return input;
	}
	
	public static double getDeposit() {
		double input = -1;
		
		
		while(input < 0) {
			System.out.println("enter amount greater than 0");
			if(!sc.hasNextDouble()) {
				sc.nextLine();
				continue;
			}
			
			input = sc.nextDouble();
			
		}
		
		return input;
	}
	
	public static double getWithdrawal(double bal) {
		double input = -1;
		
		
		while(input < 0) {
			System.out.println("enter amount greater than 0 and less than " + bal );
			if(!sc.hasNextDouble()) {
				sc.nextLine();
				continue;
			}
			
			input = sc.nextDouble();
			
		}
		
		return input;
	}
	
	public static int getTranferIdNum() {
		int input = -1;
		
		while(input < 0) {
			System.out.println("enter a postive integer account number");
			if(!sc.hasNextDouble()) {
				sc.nextLine();
				continue;
			}
			
			input = sc.nextInt();
		}
		
		return input;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
