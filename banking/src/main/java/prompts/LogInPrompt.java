package prompts;

import java.util.Scanner;

import banking.BankingApplicationDriver;
import database.UserDao;
import models.User;

/**
 * @author takumi
 *
 *         Logs in if user inputs correct password and user name
 * 
 *         If User name doesn't exist yet, a new account is created
 * 
 *         sets user in whole application.
 */

public class LogInPrompt implements Prompt {
	private UserDao userDao = UserDao.currentImplementation;

	public Prompt run() {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println(">>Log in:");
		System.out.println(">>Type user name:");
		String userName = scan.nextLine();
		
		if (userDao.findByName(userName)==null) {
			System.out.println(">>Username not found\n>>Do you want to register as a new user?.");
			System.out.println(">>Type 1 to do so, 0 if not.");
			String responce = scan.nextLine();
			if (responce.equals("1")) {
				System.out.println(">>Enter password for this account");
				String newPW = scan.nextLine();
				User newUser = new User(userName, newPW, 0);
				userDao.save(newUser);
				BankingApplicationDriver.loggedIn = true;
				BankingApplicationDriver.userData = newUser;
				System.out.println(">>Account created successfully:");

			}
			return new MainMenuPrompt();
		}

		System.out.println(">>Type password:");
		String passWord = scan.nextLine();

		if (userDao.findByUsernameAndPassword(userName, passWord)!=null) {
			BankingApplicationDriver.loggedIn = true;
			BankingApplicationDriver.userData = userDao.findByUsernameAndPassword(userName, passWord);
			System.out.println(">>Login Successful:");
			return new MainMenuPrompt();
		}

		System.out.println(">>Password incorrect");
		return new MainMenuPrompt();
	}

}
