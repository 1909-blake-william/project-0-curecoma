package prompts;

import banking.BankingApplicationDriver;
import database.AcctDao;
import database.TransDao;
import models.Account;
import models.Transaction;

public class TransactionHistoryPrompt implements Prompt {

	Long myID = BankingApplicationDriver.userData.getId();
	private TransDao transDao = TransDao.currentImplementation;
	private AcctDao acctDao = AcctDao.currentImplementation;

	@Override
	public Prompt run() {
		for (Account a : acctDao.findAll()) {
			if (a.getUserID() == myID) {
				System.out.println("Transactions for " + a.getAccountName() + ":");
				for(Transaction t:transDao.findAll()) {
					if(t.getAccountID()==a.getAccountID()) {
						System.out.println(t.toString());
					}
				}
			}
		}
		return new MainMenuPrompt();
	}
}
