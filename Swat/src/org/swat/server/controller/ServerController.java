package org.swat.server.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.swat.data.Coordinate;
import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.data.IGameInfo;
import org.swat.data.MESSAGE;
import org.swat.server.game.interaction.GameInteractionManager;
import org.swat.server.game.interaction.IGameInteraction;


public class ServerController {
	
	/**
	 * gamepersistence object
	 */
	private final GamePersistence gamepersistence;
	/**
	 * usrauthentication object
	 */
	private final UserAuthentication userauthentication;
	/**
	 * gameinteraction object
	 */
	private final IGameInteraction gameinteraction;
	/**
	 * servercontroller object
	 */
	private static ServerController _instance = null;
	
	private final static long minute = 60000;
	
	private final Timer timer;

	public static synchronized ServerController getInstance()
	{
		if (_instance == null)
			_instance = new ServerController();

		return _instance;
	}
	
	/**
	 * class constructor
	 */
	private ServerController()
	{
		timer = new Timer();
		gameinteraction = GameInteractionManager.getInstance();
		userauthentication = new UserAuthentication();
		gamepersistence = new GamePersistence();
		timer.scheduleAtFixedRate(new GamePersistenceTask(), minute, minute*20); // 1 minute delay, 10 minutes period
	}
	
	/**
	 * @author weiyu
	 * GamePersistenceTask that stores gamestates for every 10 minutes
	 */
	class GamePersistenceTask extends TimerTask {
		@Override
		public void run() {
			gamepersistence.storeGameStates(gameinteraction.getAllActiveGames());
		}
	}
	
	/**
	 * get a list of game names that are available
	 * @return returns a list of game names
	 */
	public List<String> retrieveDeployedGames() { // list of game names : string
		List<String> l = new LinkedList<String>();
		Collection<String> c = gameinteraction.getDeployedGames();
		Iterator<String> iter = c.iterator();
		
		while(iter.hasNext()) {
			   l.add(iter.next());
		}
		return l;
	}
	
	/**
	 * get the information of a certain game
	 * @param gamename receives game name
	 * @return returns the information of the game
	 */
	public IGameInfo getGameInfo(String gamename) {
			return gameinteraction.getGameInfo(gamename);
	}
	
	/**
	 * retrieve all games that are created and opened, waiting for other players to join in
	 * @return returns a list of opened gamestates
	 */
	public List<GameState> retrieveOpenGames() {
		List<GameState> l = new LinkedList<GameState>();
		Collection<GameState> c = gameinteraction.getGamesThatNeedPlayers();
		Iterator<GameState> iter = c.iterator();
		
		while(iter.hasNext()) {
			   l.add(iter.next());
		}
		return l;
	}
	
	/**
	 * player opens a game
	 * @param gameName receives game name
	 * @param username receives user(player) name
	 * @return returns the gamestate that are newly created for the player
	 */
	public GameState createGame(String gameName, String username)
	{
		return gameinteraction.createGame(getGameInfo(gameName).getGameID(),
				username);
	}
	
	/**
	 * player joins an open game
	 * @param gameid receives game id
	 * @param username receives user(player) name
	 * @return returns the gamestate after the player has joined the game
	 */
	public GameState joinGame(int gameid, String username) {
			return gameinteraction.joinGame(gameid, username);
	}
	
	/**
	 * retrieve a list of games created by player
	 * @param username receives user(player) name
	 * @return a list of gamestates
	 */
	public List<GameState> retrieveMyGame(String username) {
		
		List<GameState> l = new LinkedList<GameState>();
		Collection<GameState> c = gameinteraction.getPlayersGames(username);
		Iterator<GameState> iter = c.iterator();
		
		while(iter.hasNext()) {
			   l.add(iter.next());
		}
		
		if (l.isEmpty()) {
			List<GameState> temp = gamepersistence.getGameStates();
			List<GameState> l2 = new LinkedList<GameState>();
			Iterator<GameState> iter2 = temp.iterator();
			while (iter2.hasNext()) {
				GameState gs_temp = iter2.next();
				if (gs_temp.getPlayers().contains(username)) {
					l2.add(gs_temp);
				}
			}
			return l2;
		}
		
		return l;
	}

	/**
	 * player makes a move 
	 * @param gameInstanceID receives the id of the created game
	 * @param gameStateID receives the id of the game state
	 * @param playerUID receives the name of the user who makes this move
	 * @param coordList receives the trace of the move
	 * @return returns the new gamestate after the move
	 */
	public GameState makeMove(int gameInstanceID, int gameStateID,
			String playerUID, List<Coordinate> coordList)
	{
			GameMove gamemove = new GameMove(gameInstanceID, gameStateID,
					playerUID);
			gamemove.setMoveCoordinates(coordList);
			return gameinteraction.makeMove(gamemove);
	}
	
	/**
	 * retrieve the gamestate
	 * @param gameinstanceid receives the id of the created game
	 * @return returns the gamestate
	 */
	public GameState retrieveGameState(int gameinstanceid) {
		
		    GameState gs = gameinteraction.getGameState(gameinstanceid);
		    if (gs.getMessages().contains(MESSAGE.ILLEGAL_GAME_STATE.toString())) {
		    	List<GameState> l = gamepersistence.getGameStates();
		    	Iterator<GameState> iter = l.iterator();
		    	while(iter.hasNext()) {
		    		GameState temp = iter.next();
		    		if (temp.getGameInstanceID() == gameinstanceid) {
		    			return temp;
		    		}
		    	}
		    	return gs;
		    }
		    else {
		    	return gs;
		    }
	}
	
	/**
	 * authenticate a user for login
	 * @param username receives user name
	 * @param password receives user password
	 * @return returns the status whether login is successful
	 */
	public boolean userAuthenticate(String username, String password) {
		return userauthentication.userauthenticate(username, password);
	}
	
	/**
	 * add a user for user registration
	 * @param username receives user name
	 * @param password receives user password
	 * @return return the status whether user registration is successful
	 */
	public boolean addUser(String username, String password) {
		return userauthentication.adduser(username, password);
	}
	
}
