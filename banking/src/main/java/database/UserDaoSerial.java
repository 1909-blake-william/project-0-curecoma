package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Transaction;
import models.User;
import util.ConnectionUtil;

public class UserDaoSerial implements UserDao {

	public User extractUser(ResultSet rs) {
		long id = 0;
		String name = "";
		String password = "";
		int isadmin = 0;
		double nw = 0.0;
		try {
			id = Long.parseLong(rs.getString("USER_ID"));
			name = rs.getString("USERNAME");
			password = rs.getString("PASSWORD");
			isadmin = Integer.parseInt(rs.getString("IS_ADMIN"));
			nw = Double.parseDouble(rs.getString("NETWORTH"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new User(id, name, password, isadmin, nw);
	}

	@Override
	public long save(User u) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String str = "INSERT INTO bank_users (user_id, username, password, is_admin, networth) VALUES (?,?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(str);
			ps.setString(1, "" + u.getId());
			ps.setString(2, "" + u.getName());
			ps.setString(3, "" + u.getPassword());
			if (u.isAdmin()) {
				ps.setString(4, "1");
			} else {
				ps.setString(4, "0");
			}
			ps.setString(5, "" + u.getNetWorth());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u.getId();
	}

	@Override
	public List<User> findAll() {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM bank_users";

			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<User> users = new ArrayList<>();
			while (rs.next()) {
				users.add(extractUser(rs));
			}

			return users;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User findById(long userId) {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM bank_users WHERE USER_ID = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "" + userId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			User user = extractUser(rs);

			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User findByName(String username) {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM bank_users WHERE USERNAME = ?";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "" + username);
			ResultSet rs = ps.executeQuery();
			rs.next();
			User user = extractUser(rs);

			return user;
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public void delete(long userId) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String str = "DELETE FROM bank_users WHERE user_id = ?";
			PreparedStatement ps = c.prepareStatement(str);
			ps.setString(1, "" + userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateNetworth(User u, Transaction t) {
		try (Connection c = ConnectionUtil.getConnection()) {
			String str = "UPDATE bank_users SET networth = ? WHERE user_id = ?";
			PreparedStatement ps = c.prepareStatement(str);
			double networth = t.getTransaction() + u.getNetWorth();
			ps.setString(1, "" + networth);
			ps.setString(2, "" + u.getId());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		try (Connection c = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM bank_users WHERE USERNAME = ? AND PASSWORD = ? ";

			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, "" + username);
			ps.setString(2, "" + password);
			ResultSet rs = ps.executeQuery();
			rs.next();
			User user = extractUser(rs);

			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
