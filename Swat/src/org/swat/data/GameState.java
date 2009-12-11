package org.swat.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The data object that describes the current state of a game
 * 
 * @author steve
 *
 */
public class GameState implements Serializable {
	
	private static final long serialVersionUID = 834939964183873294L;
	public static final int UNDEFINED_INSTANCE_ID = -1;
	private static int GAME_INSTANCE_ID_COUNTER = 0;

	private int counter;
	private int gameID;
	private int gameInstanceID;
	
	private int numberOfPlayersNeeded;
	private int[][] pieceInfo;
	private GAME_STATE gameState;
	private String turnOfPlayer;
	private String winnerID;
	private List<String> messages;
	private List<String> players;
	
	public GameState()
	{
		this.messages = new ArrayList<String>();
		this.players = new ArrayList<String>();
		this.gameState = GAME_STATE.CREATED;
		this.gameInstanceID = UNDEFINED_INSTANCE_ID;
		this.counter = 0;
	}

	public void instantiate()
	{
		synchronized (this)
		{
			this.gameInstanceID = GAME_INSTANCE_ID_COUNTER++;
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
			this.players.add(newPlayer);
		
		if(this.players.size() == 1)
			this.turnOfPlayer = newPlayer;
		
		if(this.numberOfPlayersNeeded == this.players.size())
			this.gameState = GAME_STATE.STARTED;

	}
	
	public synchronized void incrementCounter() {
		this.counter++;
	}
	
	@Override
	public boolean equals(Object o) {
		
		if(o instanceof GameState)
			if (((GameState) o).gameInstanceID == this.gameInstanceID
					&& ((GameState) o).counter == this.counter)
				return true;
		
		return false;
		
	}
	
	@Override
	public GameState clone() {
		
		GameState clone = new GameState();
		clone.gameInstanceID = this.gameInstanceID;
		clone.gameState = this.gameState;
		clone.messages = this.messages;
		clone.players = this.players;
		clone.pieceInfo = this.pieceInfo;
		clone.counter = this.counter;
		clone.turnOfPlayer = this.turnOfPlayer;
		clone.winnerID = this.winnerID;
		clone.gameID = this.gameID;
		clone.numberOfPlayersNeeded = this.numberOfPlayersNeeded;
		
		return clone;
		
	}

	public void setMessages(List<String> messages)
	{
		this.messages = messages;
	}

	public void setPlayers(List<String> players)
	{
		this.players = players;
	}
	
	public void updatePlayerTurns() {

		setTurnOfPlayer(players.get((getPlayerNumber(turnOfPlayer) + 1)
				% players.size()));
		
	}

	public int getNumberOfPlayersNeeded() {
		return numberOfPlayersNeeded;
	}

	public void setNumberOfPlayersNeeded(int numberOfPlayersNeeded) {
		this.numberOfPlayersNeeded = numberOfPlayersNeeded;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getPlayerNumber(String playerUID)
	{
		return players.indexOf(playerUID);
	}
	
}
