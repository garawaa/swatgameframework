package org.swat.server.controller;

import java.io.*;
import java.util.*;

public class UserAuthentication {
	
	private static final String filename = "userinfo";
	private Map<String, String> users;
	
	public UserAuthentication() {
		if (createfile()) {
			initializeUsers();
		} else {
			retrieveUsers();
		}
	}
	
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
	
	private void initializeUsers() {
		users = new HashMap ();
		adduser("weiyu","1111"); // add more users if necessary
		adduser("steve","1111");
		adduser("tom","1111");
		adduser("abhi","1111");
		storeUsers();
	}
	
	protected void adduser(String username, String password) {
		users.put(username, password);
	}
	
	private void storeUsers() {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(users);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
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
