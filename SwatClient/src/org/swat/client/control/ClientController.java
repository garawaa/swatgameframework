package org.swat.client.control;

import java.util.ArrayList;
import java.util.List;

import org.swat.client.communication.ServerInterface;
import org.swat.data.Coordinate;
import org.swat.data.GameInfo;
import org.swat.data.GameState;

/**
 * @author Abhi Keshav
 *
 */
public class ClientController 
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
	
	public static int crossIndex = 0;
	public static int circleIndex = 0;


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
		ClientController.currentDeployedGames = currentDeployedGames;
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
		ClientController.currentOpenGames = currentOpenGames;
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
		ClientController.currentMyGames = currentMyGames;
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
		ClientController.thisPlayerID = thisPlayerID;
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
		ClientController.otherPlayerID = otherPlayerID;
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
		ClientController.currentTurnPlayerID = currentTurnPlayerID;
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
		ClientController.clickedLocation = clickedLocation;
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
			for(int i=0; i< boardWidth; i+=boardWidth/3f)
			{
				if(x >= i && x <= i+boardWidth/3f)				
					break;				
				gridX+=1;
			}
			for(int i=0; i<boardHeight; i+=boardHeight/3f)
			{				
				if(y >= i && y <= i+boardHeight/3f)
					break;				
				gridY +=1;
			}			
			clickedLocation = new Coordinate(gridX, gridY);
			if(okToSubmitMove)
				okToSubmitMove = false;
			else
				okToSubmitMove = true;
		}
		return true;
	}
	
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Server interaction methods
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX	

	/**Retrieves list of deployed games
	 * @return List of games
	 */
	public static List<String> retrieveDeployedGames()
	{		
		currentDeployedGames = ServerInterface.retrieveDeployedGames();
		return currentDeployedGames;		
	}

	/**Retrieves list of open games 
	 * @return List of games
	 */
	public static List<String> retrieveOpenGames() 
	{		
		// currentOpenGames = ServerInterface.retrieveOpenGames();
		//currentOpenGames = ControlTest.retrieveOpenGames();
		
		List<String> openGamesNames = new ArrayList<String>();
		// for(GameState gameState: currentOpenGames)
		for (int i = 0; i < 3; i++)
		{
			openGamesNames.add("Tic Tac Toe");
		}
		return openGamesNames;
	}
	
	/**Retrieves list of my games
	 * @return List of games
	 */
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
	
	/**Creates new game of specified type
	 * @param gameType Type of game
	 */
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

	/**Joins specified game
	 * @param gameID Id of game
	 */
	public static void joinGame(int gameID)
	{
		//currentGameState = ServerInterface.joinGame(gameID);
		//currentGameState = ControlTest.joinGame(gameID);
		// playerOneID = currentGameState.getPlayTurnID();
		thisPlayerID = 1;
		otherPlayerID = 0; //for now set it to 0	
	}

	/**Retrieves game state of current game
	 * 
	 */
	public static void retrieveGameState()
	{
		//currentGameState = ServerInterface.retrieveGameState(currentGameState.getGameID());
		//currentGameState = ControlTest.retrieveGameState(currentGameState.getGameID());
	}
	
	/**Makes move with given list of coordinates
	 * @param coordinates List of coordinates
	 */
	public static void makeMove(List<Coordinate> coordinates)
	{
		//currentGameState = ServerInterface.makeMove(coordinates);
		//currentGameState = ControlTest.makeMove(coordinates);
	}	
	
	/** Store username and password
	 * @param user user name
	 * @param pass password
	 */
	public static void storeUserPassword(String user, String pass)
	{
		//ServerInterface.setUsername(user);
		//ServerInterface.setPassword(pass);
	}
}