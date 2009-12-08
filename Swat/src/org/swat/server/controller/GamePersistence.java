package org.swat.server.controller;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;


import org.swat.data.*;

public class GamePersistence {
	
	private static final String filename = "gamestate";
	
	
	public GamePersistence() {
		
		if (createfile()) {
			initializeGameStates();
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
	
	private void initializeGameStates() {
		this.storeGameStates(new LinkedList<GameState>());
	}
	
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
	
	public  List<GameState> getGameStates() {
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
