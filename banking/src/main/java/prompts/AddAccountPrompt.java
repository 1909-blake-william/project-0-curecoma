package prompts;

import java.util.ArrayList;
import java.util.Scanner;

import banking.BankingApplicationDriver;
import database.AcctDao;
import models.Account;
/**
 * 
 * Creates new non-admin user
 * Initiates when name input for login doesn't exist in database
 *
 */
public class AddAccountPrompt implements Prompt {
	private AcctDao acctDao = AcctDao.currentImplementation;
	private ArrayList<Account> myActList = new ArrayList<Account>();
	Long myID = BankingApplicationDriver.userData.getId();

	public Prompt run() {
		acctDao.findByUserId(myID).forEach((Account a) -> {
			myActList.add(a);
		});

		System.out.println(">>What is the name of the account you want to add?");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		for (Account a : myActList) {
			if (a.getAccountName().equals(name)) {
				System.out.println("Account with that name already exists.");
				System.out.println("Returning to main menu.");
				return new MainMenuPrompt();
			}
		}

		Account newAcc = new Account(myID, name, 0.0);
		acctDao.save(newAcc);
		myActList.add(newAcc);
		System.out.println("New account " + name + " added.");
		return new MainMenuPrompt();
	}
}
