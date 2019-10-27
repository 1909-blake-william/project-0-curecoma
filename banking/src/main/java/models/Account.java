package models;

import java.io.Serializable;

/**
 * 
 * @author takumi
 * 
 *         Creates an account Object stringed to a user
 * 
 *         accountID:ID of this account, randomly generated userID:ID of user
 *         who owns this account accountID:ID of this account, randomly
 *         generated accountName:Name of this account assighed by the owner
 *         cash:Balance of account transactionHistory:Collection of previous
 *         transactions
 *
 */

@SuppressWarnings("serial")
public class Account implements Serializable {
	private long accountID;
	private long userID;
	private String accountName;
	private double balance;

	public Account(long userID, String accountName, double cash) {// new acct
		super();
		this.accountID = (long) (Math.random() * 10000000000000000l);
		this.userID = userID;
		this.accountName = accountName;
		this.balance = cash;
	}

	public Account(Account acct, double cashInput, Transaction t) {
		super();
		this.accountID = acct.getAccountID();
		this.userID = acct.getUserID();
		this.accountName = acct.getAccountName();
		this.balance = acct.getCash() + cashInput;
	}
	
	public Account(long aID, String aName, long uID, double cashInput) {
		super();
		this.accountID = aID;
		this.userID = uID;
		this.accountName = aName;
		this.balance = cashInput;
	}

	public long getAccountID() {
		return accountID;
	}

	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public double getCash() {
		return balance;
	}

	public void setCash(double cash) {
		this.balance = cash;
	}

	@Override
	public String toString() {
		return "Account [Account ID:" + accountID + ", User ID:" + userID + ", Account name:" + accountName + ", Balance:"
				+ balance + "]";
	}
}
