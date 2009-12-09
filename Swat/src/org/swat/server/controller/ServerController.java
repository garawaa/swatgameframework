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
import org.swat.server.game.interaction.GameInteractionManager;
import org.swat.server.game.interaction.IGameInteraction;


public class ServerController {
	
	private final GamePersistence gamepersistence;
	private final UserAuthentication userauthentication;
	private final IGameInteraction gameinteraction;
	private static ServerController _instance = null;
	
	private final static long minute = 60000;
	
	private final Timer timer;

	public static synchronized ServerController getInstance()
	{
		if (_instance == null)
			_instance = new ServerController();

		return _instance;
	}
	
	private ServerController()
	{
		timer = new Timer();
		gameinteraction = GameInteractionManager.getInstance();
		userauthentication = new UserAuthentication();
		gamepersistence = new GamePersistence();
		timer.scheduleAtFixedRate(new GamePersistenceTask(), minute, minute*20); // 1 minute delay, 10 minutes period
	}
	
	class GamePersistenceTask extends TimerTask {
		@Override
		public void run() {
			gamepersistence.storeGameStates(gameinteraction.getAllActiveGames());
		}
	}
	
	public List<String> retrieveDeployedGames() { // list of game names : string
		List<String> l = new LinkedList<String>();
		Collection<String> c = gameinteraction.getDeployedGames();
		Iterator<String> iter = c.iterator();
		
		while(iter.hasNext()) {
			   l.add(iter.next());
		}
		return l;
	}
	
	public IGameInfo getGameInfo(String gamename) {
			return gameinteraction.getGameInfo(gamename);
	}
	
	public List<GameState> retrieveOpenGames() {
		List<GameState> l = new LinkedList<GameState>();
		Collection<GameState> c = gameinteraction.getGamesThatNeedPlayers();
		Iterator<GameState> iter = c.iterator();
		
		while(iter.hasNext()) {
			   l.add(iter.next());
		}
		return l;
	}
	
	public GameState createGame(String gameName, String username)
	{
		return gameinteraction.createGame(getGameInfo(gameName).getGameID(),
				username);
	}
	
	public GameState joinGame(int gameid, String username) {
			return gameinteraction.joinGame(gameid, username);
	}
	
	public List<GameState> retrieveMyGame(String username) {
		
		List<GameState> l = new LinkedList<GameState>();
		Collection<GameState> c = gameinteraction.getPlayersGames(username);
		Iterator<GameState> iter = c.iterator();
		
		while(iter.hasNext()) {
			   l.add(iter.next());
		}
		
		return l;
	}

	public GameState makeMove(int gameInstanceID, int gameStateID,
			String playerUID, List<Coordinate> coordList)
	{
			GameMove gamemove = new GameMove(gameInstanceID, gameStateID,
					playerUID);
			gamemove.setMoveCoordinates(coordList);
			return gameinteraction.makeMove(gamemove);
	}
	
	public GameState retrieveGameState(int gameinstanceid) {
			return gameinteraction.getGameState(gameinstanceid);
	}
	
	public boolean userAuthenticate(String username, String password) {
		return userauthentication.userauthenticate(username, password);
	}
	
	public boolean addUser(String username, String password) {
		return userauthentication.adduser(username, password);
	}
	
}
