package prompts;

import java.util.Scanner;

import database.UserDao;
import models.User;

/**
 * 
 * @author takumi
 *
 *
 *         grant/removes admin privilage from user
 *
 *         deletes user if not admin.
 *
 *
 */
public class AlterUserPrompt implements Prompt {

	private UserDao userDao = UserDao.currentImplementation;

	int choice;
	long idOI;
	User user;

	@SuppressWarnings("resource")
	@Override
	public Prompt run() {
		for (User a : userDao.findAll()) {
			System.out.println(a.toString());
		}

		System.out.println(">>Which user do you want to alter");
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
		System.out.println("0: Delete Account");

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
			System.out.println(">>Are you sure to delete this user?");
			System.out.println(">>To confirm, input the letter 'y'.");
			String input = scan.nextLine().toUpperCase();
			if (input.equals("Y") && user.isAdmin()) {
				userDao.delete(user.getId());
				System.out.println(">>Account successfully deleted.");
			}
			if (input.equals("Y") && user.isAdmin()) {
				System.out.println(">>Cannot delete admin account.");
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
