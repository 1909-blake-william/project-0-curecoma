package database;

import java.util.List;

import models.Account;
import models.Transaction;

public interface AcctDao {
	AcctDao currentImplementation = new AcctDaoSerial();

	long save(Account a);

	long updateBalance(Account a, Transaction t);

	List<Account> findAll();

	List<Account> findByUserId(long l);
	
	Account findByAccountId(long l);

	void delete(Account a);

}
