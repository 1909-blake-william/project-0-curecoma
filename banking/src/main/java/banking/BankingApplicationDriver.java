package banking;

import models.User;
import prompts.MainMenuPrompt;
import prompts.Prompt;

/**
 * 
 * @author takumi
 *
 *         main system of banking application
 * 
 *         loggedIn:Check if user is logged in database:List of all users,
 *         stored in database.txt userData:User of interest at the time, set at
 *         login allAccounts:List of all accounts, stored in accounts.txt
 *         allTransactions:List of all transactions, stored in transactions.txt
 *
 */

public final class BankingApplicationDriver {

	public static boolean loggedIn; // login state
	public static User userData; // user of interest

	public static void main(String[] args) {
		loggedIn = false;
		Prompt p = new MainMenuPrompt();
		while (true) {
			p = p.run();
		}
	}
}
