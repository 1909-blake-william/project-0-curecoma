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

public class WithdrawlPrompt implements Prompt {

	Long myID = BankingApplicationDriver.userData.getId();
	private TransDao transDao = TransDao.currentImplementation;
	private AcctDao acctDao = AcctDao.currentImplementation;
	private UserDao userDao = UserDao.currentImplementation;
	private ArrayList<Account> myActList = new ArrayList<Account>();

	@Override
	public Prompt run() {
		User me = userDao.findById(myID);
		acctDao.findByUserId(myID).forEach((Account a) -> {
			myActList.add(a);
		});
		System.out.println(">>Which Account do you want to withdraw from?");
		for (int i = 0; i < myActList.size(); i++) {
			System.out.println(myActList.get(i).toString());
		}
		System.out.println(">>Input Account name you want to withdraw from.");
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

		System.out.println(">>How much do you want to withdrawl?");
		System.out.println(">>Current Balance: $" + dealWith.getCash());
		double cashInput = 0;
		try {
			cashInput = Double.parseDouble(scan.nextLine());
		} catch (Exception e) {
			System.out.println(">>Invalid input.");
			System.out.println(">>Returning to main menu.");
		}

		cashInput = (double) ((int) Math.floor(cashInput * 100)) / 100.0;
		if (cashInput > dealWith.getCash()) {
			System.out.println(">>Input exceeds current balance.");
			System.out.println(">>Returning to main menu.");
			return new MainMenuPrompt();
		}
		// convert transaction to negative
		// create transaction & modified account
		cashInput = cashInput * (-1.0);

		cashInput = (double) ((int) Math.floor(cashInput * 100)) / 100.0;

		Transaction t = new Transaction(dealWith.getAccountID(), cashInput);
		transDao.save(t);
		acctDao.updateBalance(dealWith, t);
		userDao.updateNetworth(me, t);
		System.out.println(">>Withdrew $" + ((-1.0)*cashInput));
		System.out.println(">>Returning to main menu.");

		return new MainMenuPrompt();
	}

}
