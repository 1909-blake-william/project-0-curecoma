package database;

import java.util.List;

import models.Transaction;
public interface TransDao {
	// implicitly public static final for fields

	TransDao currentImplementation = new TransDaoSerial();

	long save(Transaction t);

	List<Transaction> findAll();

	Transaction findById(long l);

}
