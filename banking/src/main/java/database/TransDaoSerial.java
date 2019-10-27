package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import models.Transaction;
import util.ConnectionUtil;

/**
 * 
 * @author takumi
 *
 *         saves/retrieves data from .txt
 * 
 *         creates list of all transactions not
 * 
 *         sure if I need fond by ID
 *
 */

public class TransDaoSerial implements TransDao {
	private TransDao transDao = TransDao.currentImplementation;

	Transaction extractTransaction(ResultSet rs) throws SQLException {
		long aid = Long.parseLong(rs.getString("ACCOUNT_ID"));
		long tid = Long.parseLong(rs.getString("TRANSACTION_ID"));
		double db = Double.parseDouble(rs.getString("TRANSACTION"));
		LocalDateTime time = LocalDateTime.parse(rs.getString("TRANSACTION_TIME"));
		return new Transaction(aid, tid, db, time);
	}

	@Override
	public long save(Transaction t) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String str = "INSERT INTO bank_transactions (transaction_id, transaction_time, transaction, account_id) VALUES (?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(str);
			ps.setString(1, "" + t.getTransactionID());
			ps.setString(2, "" + t.getTransactionTime().toString());
			ps.setString(3, "" + t.getTransaction());
			ps.setString(4, "" + t.getAccountID());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t.getTransactionID();
	}

	@Override
	public List<Transaction> findAll() {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM bank_transactions";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<Transaction> transactions = new ArrayList<>();

			while (rs.next()) {
				transactions.add(extractTransaction(rs));
			}

			return transactions;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Transaction findById(long l) {
		for (Transaction t : transDao.findAll()) {
			if (t.getAccountID() == l) {
				return t;
			}
		}
		return null;
	}

}
