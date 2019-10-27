package models;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * 
 * @author takumi
 * 
 *         Creates an Transaction Object stringed to a account
 * 
 *         accountID:ID of account which the transaction occurred
 *         transactionID:ID of this transaction, randomly assigned
 *         transaction:Change in balance
 *         transactionTime:Date/time which transaction occurred
 *
 */

@SuppressWarnings("serial")
public class Transaction implements Serializable, Comparable<Object> {
	private long accountID;
	private long transactionID;
	private Double transaction;
	private LocalDateTime transactionTime;

	public Transaction(long accountID, Double transaction) {
		super();
		this.accountID = accountID;
		this.transactionID = (long) (Math.random() * 10000000000000000l);
		this.transaction = transaction;
		this.transactionTime = LocalDateTime.now();
	}
	public Transaction(long accountID, long transID,Double transaction, LocalDateTime t) {
		super();
		this.accountID = accountID;
		this.transactionID = transID;
		this.transaction = transaction;
		this.transactionTime = t;
	}
	@Override
	public int compareTo(Object t) {
		if (getTransactionTime() == null || ((Transaction) t).getTransactionTime() == null) {
			return 0;
		}
		return getTransactionTime().compareTo(((Transaction) t).getTransactionTime());
	}

	public long getAccountID() {
		return accountID;
	}

	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}

	public long getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(long transactionID) {
		this.transactionID = transactionID;
	}

	public Double getTransaction() {
		return transaction;
	}

	public void setTransaction(Double transaction) {
		this.transaction = transaction;
	}

	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(LocalDateTime transactionTime) {
		this.transactionTime = transactionTime;
	}

	@Override
	public String toString() {
		return "Transactions [Account ID=" + accountID + ", Transaction ID=" + transactionID + ", Change ="
				+ transaction + ", Transaction time=" + transactionTime + "]";
	}

}
