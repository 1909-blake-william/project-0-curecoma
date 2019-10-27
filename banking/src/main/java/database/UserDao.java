package database;

import java.sql.ResultSet;
import java.util.List;

import models.Transaction;
import models.User;

public interface UserDao {

	UserDao currentImplementation = new UserDaoSerial();

	/**
	 * used to save a new user
	 * 
	 * @param u the user to be created
	 * @return the generated id for the user
	 */
	User extractUser(ResultSet rs);
	
	long save(User u);

	List<User> findAll();

	User findById(long userId);

	void delete(long userId);

	User findByUsernameAndPassword(String username, String password);

	User findByName(String username);

	void updateNetworth(User me, Transaction t);
	
}
