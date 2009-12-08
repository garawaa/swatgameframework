package org.swat.data;

import java.util.Map;

import org.swat.server.game.IGameInfo;


public class GameInfo implements IGameInfo
{
	private int gameID;
	private String gameName;
	private GAME_TYPE gameType;
	private int numPlayersNeeded;
	private int boardLength;
	private int boardWidth;
	private GameState initialState;
	private Map<Integer, String> pieces;
	
	public GameInfo() {}

	@Override
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

	@Override
	public int getGameID() {
		return gameID;
	}

	public void setPieces(Map<Integer, String> pieces) {
		this.pieces = pieces;
	}

	@Override
	public Map<Integer, String> getPieces() {
		return pieces;
	}

	@Override
	public GAME_TYPE getGameType() {
		return gameType;
	}

	public void setGameType(GAME_TYPE gameType) {
		this.gameType = gameType;
	}

	@Override
	public int getNumPlayersNeeded() {
		return numPlayersNeeded;
	}

	public void setNumPlayersNeeded(int numPlayersNeeded) {
		this.numPlayersNeeded = numPlayersNeeded;
	}

	@Override
	public int getBoardLength() {
		return boardLength;
	}

	public void setBoardLength(int boardLength) {
		this.boardLength = boardLength;
	}

	@Override
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
