package org.swat.server.game;

import java.util.List;

public class GameState {
	
	private int ID;
	private int gameID;
	private int gameInstanceID;
	
	private int[][] pieceInfo;
	private GAME_STATES gameState;
	private int turnOfPlayer;
	private int winnerID;
	private List<String> messages;
	
	/*
	 * Property accessors
	 */
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	public int[][] getPieceInfo() {
		return pieceInfo;
	}
	public void setPieceInfo(int[][] pieceInfo) {
		this.pieceInfo = pieceInfo;
	}
	public GAME_STATES getGameState() {
		return gameState;
	}
	public void setGameState(GAME_STATES gameState) {
		this.gameState = gameState;
	}
	public int getTurnOfPlayer() {
		return turnOfPlayer;
	}
	public void setTurnOfPlayer(int turnOfPlayer) {
		this.turnOfPlayer = turnOfPlayer;
	}
	public int getWinnerID() {
		return winnerID;
	}
	public void setWinnerID(int winnerID) {
		this.winnerID = winnerID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getGameInstanceID() {
		return gameInstanceID;
	}
	public void setGameInstanceID(int gameInstanceID) {
		this.gameInstanceID = gameInstanceID;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void addMessage(String message) {
		this.messages.add(message);
	}
	public void emptyMessages() {
		this.messages.clear();
	}
	/*
	 * (end) Property accessors
	 */
	
}
