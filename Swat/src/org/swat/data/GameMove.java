package org.swat.data;

import java.util.ArrayList;


public class GameMove {
	
	private int gameID;
	private int gameInstanceID;
	private int counter;
	
	private String playerUID;
	private ArrayList<MoveCoordinate> moveCoordinates;
	
	public GameMove(int gameID, int gameInstanceID, int gameStateID,
			String playerUID) {
		super();
		this.gameID = gameID;
		this.gameInstanceID = gameInstanceID;
		this.counter = gameStateID;
		this.playerUID = playerUID;
	}
	
	
	/*
	 * Getters and setters
	 */
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public int getGameInstanceID() {
		return gameInstanceID;
	}
	public void setGameInstanceID(int gameInstanceID) {
		this.gameInstanceID = gameInstanceID;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public String getPlayerUID() {
		return playerUID;
	}
	public void setPlayerUID(String playerUID) {
		this.playerUID = playerUID;
	}	/*
	 * (end) Getters and setters
	 */


	public void addMoveCoordinate(MoveCoordinate moveCoordinate) {
		this.moveCoordinates.add(moveCoordinate);
	}


	public ArrayList<MoveCoordinate> getMoveCoordinates() {
		return moveCoordinates;
	}
	

}
