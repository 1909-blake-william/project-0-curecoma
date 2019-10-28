package prompts;

import java.util.ArrayList;
import java.util.Scanner;

import database.AcctDao;
import database.UserDao;
import models.Account;
import models.User;

/**
 *
 *
 * grant/removes admin privilage from user
 * 
 * can delete user if not admin.
 *
 *
 */
public class AlterUserPrompt implements Prompt {

	private UserDao userDao = UserDao.currentImplementation;
	private AcctDao acctDao = AcctDao.currentImplementation;
	private ArrayList<Account> myActList = new ArrayList<Account>();

	int choice;
	long idOI;
	User user;

	@SuppressWarnings("resource")
	@Override
	public Prompt run() {
		for (User a : userDao.findAll()) {
			System.out.println(a.toString());
		}

		System.out.println(">>Which user do you want to alter?");
		System.out.println(">>Specify by ID");
		Scanner scan = new Scanner(System.in);
		try {
			idOI = Long.parseLong(scan.nextLine());
		} catch (Exception e) {
			System.out.println(">>Invalid input.");
			System.out.println(">>Returning to main menu.");
			return new MainMenuPrompt();
		}

		for (User a : userDao.findAll()) {
			if (idOI == a.getId()) {
				user = a;
				break;
			}
		}

		System.out.println(">>Altering data of:");
		System.out.println(user.toString());

		System.out.println("\n>>What changes do you want to apply?");

		System.out.println("1: Give admin privilige");
		System.out.println("2: Remove admin privilige");
		System.out.println("3: Delete disabled Account");
		System.out.println("0: Delete User");

		try {
			choice = Integer.parseInt(scan.nextLine());
		} catch (Exception e) {
			System.out.println(">>Invalid input.");
			System.out.println(">>Returning to main menu.");
			return new MainMenuPrompt();
		}

		switch (choice) {
		case (1):
			user.setAdmin(true);
			userDao.save(user);
			System.out.println(">>Privilige set.");
			break;
		case (2):
			user.setAdmin(false);
			userDao.save(user);
			System.out.println(">>Privilige set.");
			break;
		case (3):
			System.out.println(">>Which disabled account do you want to delete?");
			acctDao.findByUserId(idOI).forEach((Account a) -> {
				if (a.isIsactive()==false) {
					myActList.add(a);
				}
			});

			myActList.forEach((Account a) -> {
				System.out.println(a.toStringAdmin());
			});

			System.out.println(">>Specify by name.");
			String name = scan.nextLine();
			for (Account a : acctDao.findAll()) {
				if (a.getAccountName().equals(name)) {
					if (a.getCash() != 0.0) {
						System.out.println(">>Deletion denied.");
						System.out.println(">>Make sure that the account has no cash in it before deleting.");
						System.out.println(">>Returning to main menu.");
						return new MainMenuPrompt();
					}
					acctDao.delete_terminal(a);
					System.out.println(">>Account removed succesfully.");
					return new MainMenuPrompt();
				}
			}
			break;
		case (0):
			System.out.println(">>Are you sure to delete this user?");
			System.out.println(">>To confirm, input the letter 'y'.");
			String input = scan.nextLine().toUpperCase();
			if (input.equals("Y") && user.isAdmin()) {
				userDao.delete(user.getId());
				System.out.println(">>User successfully deleted.");
			}
			if (input.equals("Y") && user.isAdmin()) {
				System.out.println(">>Cannot delete admin user.");
			} else {

				System.out.println(">>Deletion cancelled.");
				System.out.println(">>Returning to main menu.");
				return new MainMenuPrompt();
			}
			break;
		default:
			System.out.println(">>Invalid input.");
			System.out.println(">>Returning to main menu.");
			return new MainMenuPrompt();
		}
		System.out.println(">>Operation complete.");
		System.out.println(">>Returning to main menu.");
		return new MainMenuPrompt();
	}
}
