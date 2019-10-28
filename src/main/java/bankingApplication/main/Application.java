package bankingApplication.main;

import bankingApplication.menu.MainMenu;
import bankingApplication.menu.Menu;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello");
		Menu menu = new MainMenu();
		while (menu != null) {
			menu = menu.process();
		}
		System.out.println("Good bye!");

	}

}
