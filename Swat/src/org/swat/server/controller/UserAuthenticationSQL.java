package org.swat.server.controller;

import java.sql.*;

public class UserAuthenticationSQL {
	
	private static final String url = "jdbc:mysql://localhost";
	private static final String user = "weiyu";
	private static final String password = "1111";
	
	public UserAuthenticationSQL() {
		initializeUsers();
	}
	
	private static void initializeUsers() {
		Connection conn = null;
		Statement stmt = null;
		
		// drop and then create the table
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			stmt.execute("DROP TABLE userinfo");
			stmt.execute("CREATE TABLE userinfo (UserName varchar(60) NOT NULL, Password varchar(60) NOT NULL, PRIMARY KEY (UserName))");
			stmt.execute("INSERT INTO userinfo VALUES ('weiyu', '1111')");
			stmt.execute("INSERT INTO userinfo VALUES ('steve', '1111')");
			stmt.execute("INSERT INTO userinfo VALUES ('abhi', '1111'");
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
	
	public boolean userauthenticate(String username, String password) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		boolean flag;
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT Password FROM userinfo WHERE UserName = '" + username + "'");
			if (rs.next()) {
				if (password.equals((String)rs.getObject(1))) {
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
	

}
