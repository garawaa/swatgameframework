package org.swat.game;

public class GameMove {
	
	private int gameID;
	private int gameInstanceID;
	private int gameStateID;
	
	private int playerID;
	private int fromX, fromY, toX, toY;
	
	public GameMove(int gameID, int gameInstanceID, int gameStateID,
			int playerID) {
		super();
		this.gameID = gameID;
		this.gameInstanceID = gameInstanceID;
		this.gameStateID = gameStateID;
		this.playerID = playerID;
	}
	
	public void setDetails(int fromX, int fromY, int toX, int toY) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
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
	public int getGameStateID() {
		return gameStateID;
	}
	public void setGameStateID(int gameStateID) {
		this.gameStateID = gameStateID;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public int getFromX() {
		return fromX;
	}
	public void setFromX(int fromX) {
		this.fromX = fromX;
	}
	public int getFromY() {
		return fromY;
	}
	public void setFromY(int fromY) {
		this.fromY = fromY;
	}
	public int getToX() {
		return toX;
	}
	public void setToX(int toX) {
		this.toX = toX;
	}
	public int getToY() {
		return toY;
	}
	public void setToY(int toY) {
		this.toY = toY;
	}
	/*
	 * (end) Getters and setters
	 */
	

}
