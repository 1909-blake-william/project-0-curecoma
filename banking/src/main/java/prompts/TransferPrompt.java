package prompts;

import java.util.ArrayList;
import java.util.Scanner;

import banking.BankingApplicationDriver;
import database.AcctDao;
import database.TransDao;
import models.Account;
import models.Transaction;

public class TransferPrompt implements Prompt {

	private TransDao transDao = TransDao.currentImplementation;
	private AcctDao acctDao = AcctDao.currentImplementation;
	private ArrayList<Account> myActList = new ArrayList<Account>();
	Long myID = BankingApplicationDriver.userData.getId();

	@Override
	public Prompt run() {
		System.out.println(">>List of your accounts:");

		acctDao.findByUserId(myID).forEach((Account a) -> {
			myActList.add(a);
			System.out.println(a.toString());
		});

		System.out.println(">>Input Account name you want to transfer from.");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		Account from = null;
		Account to = null;
		for (int i = 0; i < myActList.size(); i++) {
			if (input.equals(myActList.get(i).getAccountName())) {
				from = myActList.get(i);
				break;
			}
		}

		if (from == null) {
			System.out.println(">>Account of specified name not found.");
			System.out.println(">>Returning to main menu.");
			return new MainMenuPrompt();
		}

		System.out.println(">>Input Account name you want to transfer to.");
		;
		input = scan.nextLine();
		for (int i = 0; i < myActList.size(); i++) {
			if (input.equals(myActList.get(i).getAccountName())) {
				to = myActList.get(i);
				break;
			}
		}
		if (to == null) {
			System.out.println(">>Account of specified name not found.");
			System.out.println(">>Returning to main menu.");
			return new MainMenuPrompt();
		}
		System.out.println(">>Transferring from " + from.getAccountName() + " to " + to.getAccountName());
		System.out.println(">>How much do you want to transfer?");

		double cashInput = 0;
		try {
			cashInput = Double.parseDouble(scan.nextLine());
		} catch (Exception e) {
			System.out.println(">>Invalid input.");
			System.out.println(">>Returning to main menu.");
			return new MainMenuPrompt();
		}

		cashInput = (double) ((int) Math.floor(cashInput * 100)) / 100.0;

		Transaction t = new Transaction(from.getAccountID(), ((-1.0) * cashInput));
		Transaction u = new Transaction(to.getAccountID(), cashInput);
		transDao.save(t);
		transDao.save(u);
		acctDao.updateBalance(from, t);
		acctDao.updateBalance(to, u);
		System.out.println(">>Transferred $" + cashInput);
		System.out.println(">>Returning to main menu.");

		return new MainMenuPrompt();
	}
}
