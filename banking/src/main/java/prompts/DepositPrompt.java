package prompts;

import java.util.ArrayList;
import java.util.Scanner;

import banking.BankingApplicationDriver;
import database.AcctDao;
import database.TransDao;
import database.UserDao;
import models.Account;
import models.Transaction;
import models.User;

/**
 * Deposit to an account and create new transaction
 * 
 * updates: transDao, acctDao, myActList
 * 
 * removes old account from acctDao, myActList and then adds new account with
 * updated cash
 * 
 * add transaction ID to acctDao, myActList
 */

public class DepositPrompt implements Prompt {
	private TransDao transDao = TransDao.currentImplementation;
	private AcctDao acctDao = AcctDao.currentImplementation;
	private UserDao userDao = UserDao.currentImplementation;
	private ArrayList<Account> myActList = new ArrayList<Account>();
	Long myID = BankingApplicationDriver.userData.getId();

	@Override
	public Prompt run() {
		User me = userDao.findById(myID);
		System.out.println(">>List of your accounts:");

		acctDao.findByUserId(myID).forEach((Account a) -> {
			myActList.add(a);
			System.out.println(a.toString());
		});

		System.out.println(">>Input Account name you want to deposit to.");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		Account dealWith = null;
		for (int i = 0; i < myActList.size(); i++) {
			if (input.equals(myActList.get(i).getAccountName())) {
				dealWith = myActList.get(i);
			}
		}

		if (dealWith == null) {
			System.out.println(">>Account of specified name not found.");
			System.out.println(">>Returning to main menu.");
			return new MainMenuPrompt();
		}

		System.out.println(">>How much do you want to deposit?");
		double cashInput = 0;
		try {
			cashInput = Double.parseDouble(scan.nextLine());
		} catch (Exception e) {
			System.out.println(">>Invalid input.");
			System.out.println(">>Returning to main menu.");
			return new MainMenuPrompt();
		}

		cashInput = (double) ((int) Math.floor(cashInput * 100)) / 100.0;

		Transaction t = new Transaction(dealWith.getAccountID(), cashInput);
		transDao.save(t);
		acctDao.updateBalance(dealWith, t);
		userDao.updateNetworth(me, t);
		System.out.println(">>Deposit $" + cashInput);
		System.out.println(">>Returning to main menu.");

		return new MainMenuPrompt();
	}
}
