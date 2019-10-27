package prompts;

import java.util.Scanner;

import banking.BankingApplicationDriver;
/**
 * 
 * logs out user
 *
 */
public class LogOutPrompt implements Prompt {

	@SuppressWarnings("resource")
	public Prompt run() {
		System.out.println(">>Do you really want to log out?");
		System.out.println(">>Yes: type y / No: type n");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		switch (input.toUpperCase()) {
		case ("Y"):
			System.out.println("Logout successful, returning to login menu.");
			BankingApplicationDriver.loggedIn = false;
			break;
		default:
			System.out.println(">>Didn't log out, returning to main menu.");
			break;
		}
		return new MainMenuPrompt();
	}

}
