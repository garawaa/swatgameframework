package org.swat.client.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.swat.client.data.Coordinate;
import org.swat.client.data.GameInfo;
import org.swat.client.data.GameState;
import org.swat.client.userinterface.Strings;

public class ControlTest 
{
		
	private static GameState gameState;
	private static GameInfo gameInfo;
	
	private static List<String> deployedGames;
	private static List<GameState> openGames;
	private static List<GameState> myGames;

	public static GameState createGame(String gameType)
	{
		init();
		return gameState;
	}

	public static GameState joinGame(int gameID)
	{
		init();
		return gameState;
	}

	public static GameState makeMove(List<Coordinate> coordinates)
	{
		init();
		return gameState;
	}

	public static List<String> retrieveDeployedGames()
	{		
		init();
		return deployedGames;
	}

	public static GameInfo retrieveGameInfo(String gameType)
	{
		init();
		return gameInfo;
	}

	public static GameState retrieveGameState(int gameID)
	{
		init();
		return gameState;
	}

	public static List<GameState> retrieveMyGames()
	{
		init();
		return myGames;
	}

	public static List<GameState> retrieveOpenGames()
	{
		init();
		return openGames;
	}
	
	public static void init()
	{		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put(Strings.cross, 1);
		map.put(Strings.circle, 1);
		gameInfo = new GameInfo(Strings.tictactoe, map);
		
		int [][] grid = {{0,0}, {0,1}, {0,2}, {1,0}, {1,1}, {1,2}, {2,0}, {2,1}, {2,2}};
		
		gameState = new GameState(null, 1, Strings.tictactoe, grid, 2, true, 3);
		
		deployedGames = new ArrayList<String>();
		openGames = new ArrayList<GameState>();
		myGames  = new ArrayList<GameState>();
		
		deployedGames.add(Strings.tictactoe);
		
		GameState g1 = new GameState(null, 1, Strings.tictactoe, grid, 2, false, 3);
		GameState g2 = new GameState(null, 1, Strings.tictactoe, grid, 2, false, 3);
		
		openGames.add(g1);
		openGames.add(g2);
		
		myGames.add(g1);
		myGames.add(g2);
	}
}
