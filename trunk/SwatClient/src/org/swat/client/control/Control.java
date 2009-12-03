package org.swat.client.control;

import java.util.ArrayList;
import java.util.List;

import org.swat.data.Coordinate;
import org.swat.data.GameInfo;
import org.swat.data.GameState;

public class Control 
{
	/*Methods to be used: retrieveDeployedGames, retrieveOpenGames, createGame, joinGame, 
	 * retrieveMyGame, makeMove, retrieveGameState :*/
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Private members
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX	

	private static GameInfo currentGameInfo;
	private static GameState currentGameState;

	private static List<String> currentDeployedGames;
	private static List<GameState> currentOpenGames;
	private static List<GameState> currentMyGames;
	
	private static int thisPlayerID;
	private static int otherPlayerID;	
	private static int currentTurnPlayerID;
	
	private static Coordinate clickedLocation;
	
	public static boolean okToSubmitMove = false;
	
	public static final int boardWidth = 320;
	public static final int boardHeight = 320;
	
	private static boolean tempTestTurn = false;

	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Getters and setters
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX	
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
	public static void setThisPlayerID(int thisPlayerID) {
		Control.thisPlayerID = thisPlayerID;
	}

	/**
	 * @return the playerOneID
	 */
	public static int getThisPlayerID() {
		return thisPlayerID;		
	}

	/**
	 * @param playerTwoID the playerTwoID to set
	 */
	public static void setOtherPlayerID(int otherPlayerID) {
		Control.otherPlayerID = otherPlayerID;
	}

	/**
	 * @return the playerTwoID
	 */
	public static int getOtherPlayerID() {
		return otherPlayerID;
	}	
	
	/**
	 * @param currentTurnPlayerID the currentTurnPlayerID to set
	 */
	public static void setCurrentTurnPlayerID(int currentTurnPlayerID) {
		Control.currentTurnPlayerID = currentTurnPlayerID;
	}

	/**
	 * @return the currentTurnPlayerID
	 */
	public static int getCurrentTurnPlayerID() {
		//return currentTurnPlayerID; //test
		if(tempTestTurn)
		{
			tempTestTurn = false;
			return 0;
		}
		else
		{
			tempTestTurn = true;
			return 1;
		}
	}
	
	/**
	 * @param clickedLocation the clickedLocation to set
	 */
	public static void setClickedLocation(Coordinate clickedLocation) {
		Control.clickedLocation = clickedLocation;
	}

	/**
	 * @return the clickedLocation
	 */
	public static Coordinate getClickedLocation() {
		return clickedLocation;
	}	

	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Misc functions
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX	
	public static boolean setClickedLocation(int x, int y)
	{		
		//If clicked location is outside boundaries, do nothing
		if(x > boardWidth || y > boardHeight)
		{
			okToSubmitMove = false;
			return false;
		}
		else
		{
			int gridX=0, gridY=0;
			for(int i=0; i< boardWidth; i+=(float)boardWidth/3f)
			{
				if(x >= i && x <= i+(float)boardWidth/3f)				
					break;				
				gridX+=1;
			}
			for(int i=0; i<boardHeight; i+=(float)boardHeight/3f)
			{				
				if(y >= i && y <= i+(float)boardHeight/3f)
					break;				
				gridY +=1;
			}			
			clickedLocation = new Coordinate(gridX, gridY);
			okToSubmitMove = true;
		}
		return true;
	}
	
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Server interaction methods
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX	

	/** Retrieves list of deployed games **/
	public static List<String> retrieveDeployedGames()
	{		
		//currentDeployedGames = ServerInterface.retrieveDeployedGames();
		List<String> list = new ArrayList<String>();
		list.add("Tic Tac Toe");
		currentDeployedGames = list;
		return currentDeployedGames;		
	}

	/** Retrieves list of open games **/
	public static List<String> retrieveOpenGames() 
	{		
		//currentOpenGames = ServerInterface.retrieveOpenGames();
		//currentOpenGames = ControlTest.retrieveOpenGames();
		
		List<String> openGamesNames = new ArrayList<String>();		
		//for(GameState gameState: currentOpenGames)
		for(int i = 0; i<3; i++)
		{
			 openGamesNames.add("Tic Tac Toe");
		}
		return openGamesNames;
	}

	/** Retrieves list of my games **/
	public static List<String> retrieveMyGames() 
	{
		//currentMyGames = ServerInterface.retrieveMyGames();
		//currentMyGames = ControlTest.retrieveMyGames();
		
		List<String> myGamesNames = new ArrayList<String>();		
		//for(GameState gameState: currentMyGames)
		for(int i = 0; i<3; i++)
		{
			// MyGamesNames.add(gameState.getGameType());
			myGamesNames.add("Tic Tac Toe");
		}
		return myGamesNames;
	}

	/** Creates new game of specified type **/
	public static void createNewGame(String gameType) 
	{
		//currentGameInfo = ServerInterface.retrieveGameInfo(gameType);					
		//currentGameState = ServerInterface.createGame(gameType);		
		//currentGameInfo = ControlTest.retrieveGameInfo(gameType);
		//currentGameState = ControlTest.createGame(gameType);
		// playerOneID = currentGameState.getPlayTurnID();
		thisPlayerID = 1;
		otherPlayerID = 0; //for now set it to 0
	}

	/** Joins specified game **/
	public static void joinGame(int gameID)
	{
		//currentGameState = ServerInterface.joinGame(gameID);
		//currentGameState = ControlTest.joinGame(gameID);
		// playerOneID = currentGameState.getPlayTurnID();
		thisPlayerID = 1;
		otherPlayerID = 0; //for now set it to 0	
	}

	/** Retrieves game state of current game **/ 
	public static void retrieveGameState()
	{
		//currentGameState = ServerInterface.retrieveGameState(currentGameState.getGameID());
		//currentGameState = ControlTest.retrieveGameState(currentGameState.getGameID());
	}
	
	/** Makes move with given list of coordinates **/
	public static void makeMove(List<Coordinate> coordinates)
	{
		//currentGameState = ServerInterface.makeMove(coordinates);
		//currentGameState = ControlTest.makeMove(coordinates);
	}	
}