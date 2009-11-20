package org.swat.data;

import java.util.Map;

import org.swat.server.game.GAME_TYPE;

public class GameInfo
{
	private int gameID;
	private String gameName;
	private GAME_TYPE gameType;
	private int numPlayersNeeded;
	private int boardLength;
	private int boardWidth;
	private GameState initialState;
	private Map<Integer, String> pieces;

	public String getGameName()
	{
		return gameName;
	}

	public void setGameName(String gameName)
	{
		this.gameName = gameName;
	}


	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public int getGameID() {
		return gameID;
	}

	public void setPieces(Map<Integer, String> pieces) {
		this.pieces = pieces;
	}

	public Map<Integer, String> getPieces() {
		return pieces;
	}

	public GAME_TYPE getGameType() {
		return gameType;
	}

	public void setGameType(GAME_TYPE gameType) {
		this.gameType = gameType;
	}

	public int getNumPlayersNeeded() {
		return numPlayersNeeded;
	}

	public void setNumPlayersNeeded(int numPlayersNeeded) {
		this.numPlayersNeeded = numPlayersNeeded;
	}

	public int getBoardLength() {
		return boardLength;
	}

	public void setBoardLength(int boardLength) {
		this.boardLength = boardLength;
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public void setBoardWidth(int boardWidth) {
		this.boardWidth = boardWidth;
	}

	public GameState getInitialState() {
		return initialState;
	}

	public void setInitialState(GameState initialState) {
		this.initialState = initialState;
	}

}
