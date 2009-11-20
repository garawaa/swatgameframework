package org.swat.data;

import java.util.ArrayList;
import java.util.List;

import org.swat.server.game.Game;

public class GameState {
	
	private int counter;
	private int gameID;
	private int gameInstanceID;
	
	private int[][] pieceInfo;
	private GAME_STATE gameState;
	private String turnOfPlayer;
	private String winnerID;
	private List<String> messages;
	private ArrayList<String> players;
	
	private Game game;
	
	public GameState(Game game) {
		this.game = game;
	}
	
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
	public GAME_STATE getGameState() {
		return gameState;
	}
	public void setGameState(GAME_STATE gameState) {
		this.gameState = gameState;
	}
	public String getTurnOfPlayer() {
		return turnOfPlayer;
	}
	public void setTurnOfPlayer(String turnOfPlayer) {
		this.turnOfPlayer = turnOfPlayer;
	}
	public String getWinnerID() {
		return winnerID;
	}
	public void setWinnerID(String winnerID) {
		this.winnerID = winnerID;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
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
	
	public List<String> getPlayers() {
		return players;
	}
	
	public void addPlayer(String newPlayer) {

		if(this.game.getNumberOfPlayersNeeded() > this.players.size())
			this.players.add(newPlayer);
		
		if(this.game.getNumberOfPlayersNeeded() == this.players.size())
			this.gameState = GAME_STATE.STARTED;

	}
	
	public int getPlayerNumber(String playerUID) {
		
		int playerNumber = -1;
		String[] playerUIDs = new String[players.size()];
		playerUIDs = players.toArray(playerUIDs);
		
		for(int loop1=0; loop1<playerUIDs.length; loop1++)
			if(playerUIDs[loop1].equals(playerUID))
				playerNumber = loop1+1;
		
		return (playerNumber);
		
	}
	
	public synchronized void incrementCounter() {
		this.counter++;
	}
	
}
