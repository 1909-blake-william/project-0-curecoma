package models;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {
	private long id;
	private String name;
	private String Password;
	private boolean isAdmin;
	private double netWorth;

	public User(String userName, String newPW, int j) {
		this.id = (long) (Math.random() * 10000000000000000l);
		this.name = userName;
		this.Password = newPW;
		this.isAdmin = j == 1 ? true : false;
		this.netWorth = 0;
	}

	public User(long id, String userName, String newPW, int j, double nw) {
		this.id = id;
		this.name = userName;
		this.Password = newPW;
		this.isAdmin = j == 1 ? true : false;
		this.netWorth = nw;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "User [User name:" + name + ", Net worth:" + netWorth + ", User ID:" + id + ", Admin status:" + isAdmin
				+ "]";
	}

	public double getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(double netWorth) {
		this.netWorth = netWorth;
	}
}
