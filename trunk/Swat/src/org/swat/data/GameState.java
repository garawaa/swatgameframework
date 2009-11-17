package org.swat.data;

import java.util.List;

public class GameState
{
	private List<String> currentMessages;
	private int gameID;
	private String gameType;
	private int grid[][];
	private int playTurnID;
	private boolean started;
	private String winnerID;

	public List<String> getCurrentMessage()
	{
		return currentMessages;
	}

	public int getGameID()
	{
		return gameID;
	}

	public String getGameType()
	{
		return gameType;
	}

	public int[][] getGrid()
	{
		return grid;
	}

	public int getPlayTurnID()
	{
		return playTurnID;
	}

	public String getWinnerID()
	{
		return winnerID;
	}

	public boolean isStarted()
	{
		return started;
	}

	public void setCurrentMessages(List<String> currentMessages)
	{
		this.currentMessages = currentMessages;
	}

	public void setGameID(int gameID)
	{
		this.gameID = gameID;
	}

	public void setGameType(String gameType)
	{
		this.gameType = gameType;
	}

	public void setGrid(int[][] grid)
	{
		this.grid = grid;
	}

	public void setPlayTurnID(int playTurnID)
	{
		this.playTurnID = playTurnID;
	}

	public void setStarted(boolean started)
	{
		this.started = started;
	}

	public void setWinnerID(String winnerID)
	{
		this.winnerID = winnerID;
	}
}
