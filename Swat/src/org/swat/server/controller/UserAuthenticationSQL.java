package org.swat.server.controller;

import java.sql.*;

public class UserAuthenticationSQL {
	
	/**
	 * database URL
	 */
	private static final String url = "jdbc:mysql://localhost:3306";
	/**
	 * database usrname
	 */
	private static final String user = "root";
	/**
	 * database password
	 */
	private static final String password = "1111";
	
	/**
	 * class constructor
	 */
	public UserAuthenticationSQL() {
		initializeUsers();
	}
	
	/**
	 * create a userinfo table and set up the administrators
	 */
	private static void initializeUsers() {
		Connection conn = null;
		Statement stmt = null;
		
		// drop and then create the table
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			stmt.execute("CREATE TABLE userinfo (UserName varchar(60) NOT NULL, Password varchar(60) NOT NULL, PRIMARY KEY (UserName))");
			stmt.execute("INSERT INTO userinfo VALUES ('weiyu', '1111')");
			stmt.execute("INSERT INTO userinfo VALUES ('steve', '1111')");
			stmt.execute("INSERT INTO userinfo VALUES ('abhi', '1111')");
			stmt.execute("INSERT INTO userinfo VALUES ('tom', '1111')"); // add new users
		} catch (SQLException ex) {
			ex.getStackTrace();
		} finally {
			try {
				stmt.close();
			    conn.close();
			} catch (SQLException ex) {
				ex.getStackTrace();
			} finally {
				stmt = null;
				conn = null;
			}
		}
	}
	
	/**
	 * authenticate a user (login)
	 * @param username receives a user name
	 * @param pd receives user's password
	 * @return returns the status whether login is successful
	 */
	public boolean userauthenticate(String username, String pd) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		boolean flag;
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Password FROM userinfo WHERE UserName = '" + username + "'");
			if (rs.next()) {
				if (pd.equals((String)rs.getObject(1))) {
				   flag = true;	
				}
				else {
					flag = false;
				}
			}
			else {
				flag = false;
			}
		} catch (SQLException ex) {
			ex.getStackTrace();
			flag = false;
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException ex) {
				ex.getStackTrace();
			} finally {
				rs = null;
				stmt = null;
				conn = null;
			}
		}
		return flag;
	}
	
	/**
	 * add a new user (user registration)
	 * @param username receives a user name
	 * @param pd receives a user password
	 * @return returns the status whether user registration is successful
	 */
	public boolean addUser(String username, String pd) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		boolean flag;
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Password FROM userinfo WHERE UserName = '" + username + "'");
			if (rs.next()) {
				flag = false;
			}
			else {
				stmt.execute("INSERT INTO userinfo VALUES('" + username + "', '" + pd + "')");
				flag = true;
			}
		} catch (SQLException ex) {
			ex.getStackTrace();
			flag = false;
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException ex) {
				ex.getStackTrace();
			} finally {
				rs = null;
				stmt = null;
				conn = null;
			}
		}
		return flag;
	}
	

}
