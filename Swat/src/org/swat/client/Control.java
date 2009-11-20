package org.swat.client.control;

import java.util.ArrayList;
import java.util.List;

import org.swat.client.data.Coordinate;
import org.swat.client.data.GameInfo;
import org.swat.client.data.GameState;

public class Control 
{
	/*Methods to be used: retrieveDeployedGames, retrieveOpenGames, createGame, joinGame, 
	 * retrieveMyGame, makeMove, retrieveGameState :*/

	private static GameInfo currentGameInfo;
	private static GameState currentGameState;

	private static List<String> currentDeployedGames;
	private static List<GameState> currentOpenGames;
	private static List<GameState> currentMyGames;
	
	private static int playerOneID;
	private static int playerTwoID;

	/**
	 * @param currentGameInfo the currentGameInfo to set
	 */
	public static void setCurrentGameInfo(GameInfo gameInfo) {
		currentGameInfo = gameInfo;
	}

	/**
	 * @return the currentGameInfo
	 */
	public static GameInfo getCurrentGameInfo() {
		return currentGameInfo;
	}

	/**
	 * @param currentGameState the currentGameState to set
	 */
	public static  void setCurrentGameState(GameState gameState) {
		currentGameState = gameState;
	}

	/**
	 * @return the currentGameState
	 */
	public static GameState getCurrentGameState() {
		return currentGameState;
	}

	/**
	 * @param currentDeployedGames the currentDeployedGames to set
	 */
	public static void setCurrentDeployedGames(List<String> currentDeployedGames) {
		Control.currentDeployedGames = currentDeployedGames;
	}

	/**
	 * @return the currentDeployedGames
	 */
	public static List<String> getCurrentDeployedGames() {
		return currentDeployedGames;
	}

	/**
	 * @param currentOpenGames the currentOpenGames to set
	 */
	public static void setCurrentOpenGames(List<GameState> currentOpenGames) {
		Control.currentOpenGames = currentOpenGames;
	}

	/**
	 * @return the currentOpenGames
	 */
	public static List<GameState> getCurrentOpenGames() {
		return currentOpenGames;
	}

	/**
	 * @param currentMyGames the currentMyGames to set
	 */
	public static void setCurrentMyGames(List<GameState> currentMyGames) {
		Control.currentMyGames = currentMyGames;
	}

	/**
	 * @return the currentMyGames
	 */
	public static List<GameState> getCurrentMyGames() {
		return currentMyGames;
	}
	
	/**
	 * @param playerOneID the playerOneID to set
	 */
	public static void setPlayerOneID(int playerOneID) {
		Control.playerOneID = playerOneID;
	}

	/**
	 * @return the playerOneID
	 */
	public static int getPlayerOneID() {
		return playerOneID;
	}

	/**
	 * @param playerTwoID the playerTwoID to set
	 */
	public static void setPlayerTwoID(int playerTwoID) {
		Control.playerTwoID = playerTwoID;
	}

	/**
	 * @return the playerTwoID
	 */
	public static int getPlayerTwoID() {
		return playerTwoID;
	}

	/** Retrieves list of deployed games **/
	public static List<String> retrieveDeployedGames()
	{		
		//currentDeployedGames = ServerInterface.retrieveDeployedGames();
		currentDeployedGames = ControlTest.retrieveDeployedGames();
		return currentDeployedGames;		
	}

	/** Retrieves list of open games **/
	public static List<String> retrieveOpenGames() 
	{		
		//currentOpenGames = ServerInterface.retrieveOpenGames();
		currentOpenGames = ControlTest.retrieveOpenGames();
		
		List<String> openGamesNames = new ArrayList<String>();		
		for(GameState gameState: currentOpenGames)
		{
			openGamesNames.add(gameState.getGameType());
		}
		return openGamesNames;
	}

	/** Retrieves list of my games **/
	public static List<String> retrieveMyGames() 
	{
		//currentMyGames = ServerInterface.retrieveMyGames();
		currentMyGames = ControlTest.retrieveMyGames();
		
		List<String> MyGamesNames = new ArrayList<String>();		
		for(GameState gameState: currentMyGames)
		{
			MyGamesNames.add(gameState.getGameType());
		}
		return MyGamesNames;
	}

	/** Creates new game of specified type **/
	public static void createNewGame(String gameType) 
	{
		//currentGameInfo = ServerInterface.retrieveGameInfo(gameType);					
		//currentGameState = ServerInterface.createGame(gameType);		
		currentGameInfo = ControlTest.retrieveGameInfo(gameType);
		currentGameState = ControlTest.createGame(gameType);
		playerOneID = currentGameState.getPlayTurnID();
		playerTwoID = 0; //for now set it to 0
	}

	/** Joins specified game **/
	public static void joinGame(int gameID)
	{
		//currentGameState = ServerInterface.joinGame(gameID);
		currentGameState = ControlTest.joinGame(gameID);
		playerOneID = currentGameState.getPlayTurnID();
		playerTwoID = 0; //for now set it to 0
	}

	/** Retrieves game state of current game **/ 
	public static void retrieveGameState()
	{
		//currentGameState = ServerInterface.retrieveGameState(currentGameState.getGameID());
		currentGameState = ControlTest.retrieveGameState(currentGameState.getGameID());
	}
	
	/** Makes move with given list of coordinates **/
	public static void makeMove(List<Coordinate> coordinates)
	{
		//currentGameState = ServerInterface.makeMove(coordinates);
		currentGameState = ControlTest.makeMove(coordinates);
	}
}