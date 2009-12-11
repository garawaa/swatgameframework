package org.swat.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.swat.data.GameState;

public class GamePersistence {
	
	/**
	 * The name of the file that stores gamestates.
	 */
	private static final String filename = "gamestate";
	
	
	/**
	 * GamePersistence Class Constructor
	 */
	public GamePersistence() {
		
		if (createfile()) {
			initializeGameStates();
		}
		
	}
	
	/**
	 * Create an empty file that is used to store gamestates.
	 * @return Returns a boolean showing whether the file is successfully created.
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
	 * Recreate an empty file that is used to stores gamestates.
	 */
	private void recreatefile() {
		try {
			File file = new File(filename);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * store an empty list into the file
	 */
	private void initializeGameStates() {
		this.storeGameStates(new LinkedList<GameState>());
	}
	
	/**
	 * store a list of gamestates into a file
	 * @param l receives a list of gamestates
	 * @return returns the status whether gamestates have been stored.
	 */
	public boolean storeGameStates(Collection<GameState> l) {
		recreatefile();
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(l);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * get a list of gamestates that were stored.
	 * @return returns a list of gamestates
	 */
	@SuppressWarnings("unchecked")
	public List<GameState> getGameStates() {
		try {
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			return (List<GameState>) ois.readObject();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}	

}
