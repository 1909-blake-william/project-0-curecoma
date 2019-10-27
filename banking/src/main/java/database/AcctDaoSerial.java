package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnectionUtil;

import models.Account;
import models.Transaction;

/**
 * 
 * @author takumi
 * 
 *         save:removes old data from list and saves new list to text
 *         
 *         delete:removes given account findAll:gets all the stored data
 *         
 *         findById:gets data with ID
 *
 */

public class AcctDaoSerial implements AcctDao {

	Account extractAccount(ResultSet rs) throws SQLException {
		long aid = Long.parseLong(rs.getString("ACCOUNT_ID"));
		String name = rs.getString("ACCOUNT_NAME");
		long uid = Long.parseLong(rs.getString("USER_ID"));
		double ci = Double.parseDouble(rs.getString("BALANCE"));
		return new Account(aid, name, uid, ci);
	}

	@Override
	public long save(Account a) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String str = "INSERT INTO bank_accounts (account_id, account_name, balance, user_id) VALUES (?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(str);
			ps.setString(1, "" + a.getAccountID());
			ps.setString(2, "" + a.getAccountName());
			ps.setString(3, "" + a.getCash());
			ps.setString(4, "" + a.getUserID());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a.getAccountID();
	}

	public long updateBalance(Account a, Transaction t) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String str = "UPDATE bank_accounts SET balance = ? WHERE account_id = ?";
			PreparedStatement ps = c.prepareStatement(str);
			double balance = t.getTransaction() + a.getCash();
			ps.setString(1, "" + balance);
			ps.setString(2, "" + a.getAccountID());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a.getAccountID();
	}

	@Override
	public void delete(Account a) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String str = "DELETE FROM bank_accounts WHERE account_id = ?;";
			PreparedStatement ps = c.prepareStatement(str);
			ps.setString(1, "" + a.getAccountID());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Account> findAll() {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM bank_accounts";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Account> accounts = new ArrayList<>();

			while (rs.next()) {
				accounts.add(extractAccount(rs));
			}

			return accounts;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Account> findByUserId(long l) {
		try (Connection c = ConnectionUtil.getConnection()) {
			List<Account> myAccounts = new ArrayList<Account>();
			String sql = "SELECT * FROM bank_accounts WHERE user_id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "" + l);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				myAccounts.add(extractAccount(rs));
			}
			return myAccounts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Account findByAccountId(long l) {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM bank_accounts WHERE account_id = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "" + l);
			ResultSet rs = ps.executeQuery();
			return extractAccount(rs);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
