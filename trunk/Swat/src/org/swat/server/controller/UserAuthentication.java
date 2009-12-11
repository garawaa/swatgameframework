package org.swat.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;


public class UserAuthentication {
	
	/**
	 * the name of the file that are used to store user information
	 */
	private static final String filename = "userinfo";
	/**
	 * a map that stores user names and user passwords
	 */
	private Map<String, String> users;
	
	/**
	 * class constructor
	 */
	public UserAuthentication() {
		if (createfile()) {
			initializeUsers();
		} else {
			retrieveUsers();
		}
	}
	
	/**
	 * create a file that stores user information
	 * @return whether the file has been created successfully
	 */
	private boolean createfile() {
		try {
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
				return true;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * insert the information of administrators
	 */
	private void initializeUsers() {
		users = new HashMap<String, String>();
		adduser("weiyu","1111"); // add more users if necessary
		adduser("steve","1111");
		adduser("tom","1111");
		adduser("abhi","1111");
		storeUsers();
	}
	
	/**
	 * add a new user (user registration)
	 * @param username receives a user name
	 * @param password receives a user password
	 * @return returns the status whether user registration is successful
	 */
	public boolean adduser(String username, String password) {
		if(users.containsKey(username)) {
			return false;
		} else {
			users.put(username, password);
			return true;
		}
	}
	
	/**
	 * store user information into a file
	 */
	private void storeUsers() {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * retrieve user information from a file
	 */
	@SuppressWarnings("unchecked")
	private void retrieveUsers() {
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			users = (Map<String, String>)ois.readObject();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * authenticate a user (login)
	 * @param username receives a user name
	 * @param password receives a user password
	 * @return returns the status whether user login is successful
	 */
	public boolean userauthenticate(String username, String password) {
		if(users.containsKey(username)) {
			if (users.get(username).equals(password)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
