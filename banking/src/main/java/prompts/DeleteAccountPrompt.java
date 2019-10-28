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
 *         deleting accounts are only allowed when accounts are empty
 * 
 * @todo make it so that the account is only disconnected instead of deleted
 */

public class DeleteAccountPrompt implements Prompt {
	private AcctDao acctDao = AcctDao.currentImplementation;
	private ArrayList<Account> myActList = new ArrayList<Account>();
	Long myID = BankingApplicationDriver.userData.getId();

	public Prompt run() {
		acctDao.findByUserId(myID).forEach((Account a) -> {
			if (a.isIsactive()) {
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
