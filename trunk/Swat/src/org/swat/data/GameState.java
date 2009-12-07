package org.swat.data;

import java.util.ArrayList;
import java.util.List;

import org.swat.server.game.Game;


public class GameState {
	
	private int counter;
	private int gameInstanceID;
	
	private int[][] pieceInfo;
	private GAME_STATE gameState;
	private String turnOfPlayer;
	private String winnerID;
	private List<String> messages;
	private List<String> players;
	
	private Game game;
	
	private static int GAME_INSTANCE_ID_COUNTER = 0;
	
	public GameState(Game game) {
		this.game = game;
		this.messages = new ArrayList<String>();
		this.players = new ArrayList<String>();
		this.gameState = GAME_STATE.CREATED;
		this.counter = 0;
		
		synchronized (this) {
			GAME_INSTANCE_ID_COUNTER++;
			this.gameInstanceID = GAME_INSTANCE_ID_COUNTER;
		}
		
	}
	
	/*
	 * Property accessors
	 */
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
	
	public boolean equals(Object o) {
		
		if(o instanceof GameState)
			if(((GameState) o).gameInstanceID == this.gameInstanceID)
				return true;
		
		return false;
		
	}
	
	public GameState clone() {
		
		GameState clone = new GameState(this.game);
		clone.game = this.game;
		clone.gameInstanceID = this.gameInstanceID;
		clone.gameState = this.gameState;
		clone.messages = this.messages;
		clone.players = this.players;
		clone.pieceInfo = this.pieceInfo;
		clone.counter = this.counter;
		
		return clone;
		
	}
	
}
