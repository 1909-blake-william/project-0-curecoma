package util;

import database.UserDao;
import models.User;
import util.AuthUtil;

public class AuthUtil {
	public static final AuthUtil instance = new AuthUtil();

	private UserDao userDao = UserDao.currentImplementation;
	private User currentUser = null;

	private AuthUtil() {
		super();
	}

	public User login(String username, String password) {
		User u = userDao.findByUsernameAndPassword(username, password);
		currentUser = u;
		return u;
	}

	public User getCurrentUser() {
		return currentUser;
	}
}
