package prompts;

import java.util.ArrayList;
import java.util.Scanner;

import banking.BankingApplicationDriver;
import database.AcctDao;
import models.Account;

/**
 * 
 * @author takumi
 *
 *         deletes account specified
 *
 * @undecided should I remove the account from the database or should I just
 *            disconnect it from the accout?
 * 
 * @CurrentImplementation it currently deletes the account from the database.
 */

public class DeleteAccountPrompt implements Prompt {
	private AcctDao acctDao = AcctDao.currentImplementation;
	private ArrayList<Account> myActList = new ArrayList<Account>();
	Long myID = BankingApplicationDriver.userData.getId();

	public Prompt run() {
		acctDao.findAll().forEach((Account a) -> {
			if (a.getUserID() == myID) {
				myActList.add(a);
			}
		});
		
		System.out.println(">>Which Account do you want to delete?");
		myActList.forEach((Account a) -> {
			System.out.println(a.toString());
		});

		System.out.println(">>Specify by name.");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		for (Account a : acctDao.findAll()) {
			if (a.getAccountName().equals(name)) {
				if (a.getCash() != 0.0) {
					System.out.println(">>Deletion denied.");
					System.out.println(">>Make sure that the account has no cash in it before deleting.");
					System.out.println(">>Returning to main menu.");
					return new MainMenuPrompt();
				}
				acctDao.delete(a);
				System.out.println(">>Account removed succesfully.");
				return new MainMenuPrompt();
			}
		}

		System.out.println(">>Specified account not found.");
		System.out.println(">>Returning to main menu.");
		return new MainMenuPrompt();
	}

}
