package prompts;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import banking.BankingApplicationDriver;
import database.AcctDao;
import database.UserDao;
import models.Account;
import models.User;

/**
 * 
 * @author takumi
 *
 *         Main menu of banking application
 * 
 *         account returns here when other prompts are complete
 *
 */

public class MainMenuPrompt implements Prompt {

	private UserDao userDao = UserDao.currentImplementation;
	private AcctDao acctDao = AcctDao.currentImplementation;
	private ArrayList<Account> myActList = new ArrayList<Account>();
	Long myID;

	@SuppressWarnings("resource")
	public Prompt run() {
		if (BankingApplicationDriver.loggedIn == false) {
			return new LogInPrompt();
		}
		
		myID = BankingApplicationDriver.userData.getId();
		
		acctDao.findAll().forEach((Account a) -> {
			if (a.getUserID() == myID) {
				myActList.add(a);
			}
		});
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Scanner scan = new Scanner(System.in);
		int input = 0;
		if (BankingApplicationDriver.userData.isAdmin()) {
			System.out.println("\n>>Welcome");
			System.out.println(">>please choose an option");
			System.out.println("Enter 1 to deposit cash");
			System.out.println("Enter 2 to withdraw cash");
			System.out.println("Enter 3 to transfer cash");
			
			System.out.println("Enter 4 to view accounts");
			System.out.println("Enter 5 to transaction history of an account");
			System.out.println("Enter 6 to add accounts");
			System.out.println("Enter 7 to remove accounts");
			System.out.println("\nEnter 91 to view all users");
			System.out.println("Enter 92 to view all accounts");
			System.out.println("Enter 93 to alter account state");
			System.out.println("\nEnter 0 to log out");
			try {
				input = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("\nInvalid input");
				return new MainMenuPrompt();
			}
			switch (input) {
			case (1):
				return new DepositPrompt();
			case (2):
				return new WithdrawlPrompt();
			case (3):
				return new TransferPrompt();
			case (4):
				myActList.forEach((Account u) -> {
					System.out.println(u.toString());
				});
				return new MainMenuPrompt();
			case (5):
				return new TransactionHistoryPrompt();
			case (6):
				return new AddAccountPrompt();
			case (7):
				return new DeleteAccountPrompt();
			case (91):
				userDao.findAll().forEach((User u) -> {
					System.out.println(u.toString());
				});
				return new MainMenuPrompt();
			case (92):
				acctDao.findAll().forEach((Account u) -> {
					System.out.println(u.toString());
				});
				return new MainMenuPrompt();
			case (93):

				return new AlterUserPrompt();
			case (0):

				return new LogOutPrompt();
			default:
				System.out.println("\nInvalid input");
				return new MainMenuPrompt();
			}

		} else {
			System.out.println(">>Welcome");
			System.out.println(">>please choose an option");
			System.out.println("Enter 1 to deposit cash");
			System.out.println("Enter 2 to withdraw cash");
			System.out.println("Enter 3 to transfer cash");
			System.out.println("Enter 4 to view accounts");
			System.out.println("Enter 5 to transaction history of an account");
			System.out.println("Enter 6 to add accounts");
			System.out.println("Enter 7 to remove accounts");

			System.out.println("\nEnter 0 to log out");
			try {
				input = Integer.parseInt(scan.nextLine());
			} catch (Exception e) {
				System.out.println("\nInvalid input");
				return new MainMenuPrompt();
			}
			switch (input) {
			case (1):
				return new DepositPrompt();
			case (2):
				return new WithdrawlPrompt();
			case (3):
				return new TransferPrompt();
			case (4):
				myActList.forEach((Account u) -> {
					System.out.println(u.toString());
				});
				return new MainMenuPrompt();
			case (5):
				return new TransactionHistoryPrompt();
			case (6):
				return new AddAccountPrompt();
			case (7):
				return new DeleteAccountPrompt();
			case (0):

				return new LogOutPrompt();
			default:
				System.out.println("\nInvalid input");
				return new MainMenuPrompt();
			}
		}
	}
}
