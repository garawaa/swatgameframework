package org.swat.server.controller;

import java.util.*;

import org.swat.data.Coordinate;
import org.swat.data.GameInfo;
import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.server.game.IGameInfo;
import org.swat.server.game.exceptions.GameNotFoundException;
import org.swat.server.game.exceptions.IllegalGameJoinException;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;
import org.swat.server.game.interaction.GameInteractionManager;
import org.swat.server.game.interaction.IGameInteraction;


public class ServerController {
	
	private final GamePersistence gamepersistence;
	private final UserAuthentication userauthentication;
	private final IGameInteraction gameinteraction;
	private static ServerController _instance = null;
	
	private final static long minute = 60000;
	
	private Timer timer;

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
		try
		 {
			return gameinteraction.getGameInfo(gamename);
		 }
		 catch(GameNotFoundException ex){
			 ex.printStackTrace();
		 }
		 return null;
	}
	
	public List<GameState> retrieveOpenGames() {
		List<GameState> l = new LinkedList<GameState>();
		Collection<GameState> c = gameinteraction.getGamesThatNeedPlayers();
		Iterator<GameState> iter = c.iterator();
		
		while(iter.hasNext()) {
			   l.add((GameState)iter.next());
		}
		return l;
	}
	
	public GameState createGame(String gameName, String username)
	{
		return gameinteraction.createGame(getGameInfo(gameName).getGameID(),
				username);
	}
	
	public GameState joinGame(int gameid, String username) {
		try
		 {
			return gameinteraction.joinGame(gameid, username);
		 }
		 catch(IllegalGameJoinException ex){
			 ex.printStackTrace();
		 }
		 return null;
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
		try
		{
			GameMove gamemove = new GameMove(gameInstanceID, gameStateID,
					playerUID);
			gamemove.setMoveCoordinates(coordList);
			return gameinteraction.makeMove(gamemove);
		}
		catch (IllegalMoveException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalGameStateException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public GameState retrieveGameState(int gameinstanceid) {
		try{
			return gameinteraction.getGameState(gameinstanceid);
		}
		catch(IllegalGameStateException ex)
		{}
		
		List<GameState> l = gamepersistence.getGameStates();
		Iterator<GameState> li = l.iterator();
		while (li.hasNext()) {
			GameState gs = li.next();
			if (gs.getGameInstanceID() == gameinstanceid) {
					return gs;
			}
		}
		return null;
	}
	
	public boolean userAuthenticate(String username, String password) {
		return userauthentication.userauthenticate(username, password);
	}
	
	public boolean addUser(String username, String password) {
		return userauthentication.adduser(username, password);
	}
	
}
