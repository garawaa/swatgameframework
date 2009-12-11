package org.swat.data;

import java.util.List;

/**
 * The data object that describes a game move
 * 
 * @author steve
 *
 */
public class GameMove {
	
	private int gameInstanceID;
	private int counter;
	
	private String playerUID;
	private List<Coordinate> moveCoordinates;
	
	public GameMove(int gameInstanceID, int gameStateID,
			String playerUID) {
		super();
		this.gameInstanceID = gameInstanceID;
		this.counter = gameStateID;
		this.playerUID = playerUID;
	}
	
	
	/*
	 * Getters and setters
	 */
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


	public void addMoveCoordinate(Coordinate moveCoordinate)
	{
		this.moveCoordinates.add(moveCoordinate);
	}


	public List<Coordinate> getMoveCoordinates()
	{
		return moveCoordinates;
	}

	public void setMoveCoordinates(List<Coordinate> moveCoordinates)
	{
		this.moveCoordinates = moveCoordinates;
	}
	

}
