package org.swat.server.controller;

import java.util.*;

import org.swat.data.GameInfo;
import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.data.Coordinate;
import org.swat.server.communication.NetworkServer;
import org.swat.server.game.Game;
import org.swat.server.game.exceptions.*;
import org.swat.server.game.interaction.GameInteractionManager;


public class ServerController {
	
	private final GamePersistence gamepersistence;
	private final UserAuthentication userauthentication;
	private final GameInteractionManager gameinteraction;
	private static ServerController _instance = null;

	public static synchronized ServerController getInstance()
	{
		if (_instance == null)
			_instance = new ServerController();

		return _instance;
	}
	
	private ServerController()
	{
		gamepersistence = new GamePersistence();
		userauthentication = new UserAuthentication();
		gameinteraction = GameInteractionManager.getInstance();
	}
	
	public List<String> retrieveDeployedGames() { // list of game names : string
		List<String> l = new LinkedList();
		Collection<String> c = gameinteraction.getDeployedGames();
		Iterator iter = c.iterator();
		
		while(iter.hasNext()) {
			   l.add((String)iter.next());
		}
		
		return l;
	}
	
	public GameInfo getGameInfo(String gamename) {
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
		List<GameState> l = new LinkedList();
		Collection<GameState> c = gameinteraction.getGamesThatNeedPlayers();
		Iterator iter = c.iterator();
		
		while(iter.hasNext()) {
			   l.add((GameState)iter.next());
		}
		
		return l;
	}
	
	//public retrieveGameInfo()
	
	//who create a game?
	public GameState createGame(int gameid, String username) {
		return gameinteraction.createGame(gameid, username);
	}
	
	//gameinstanceid ?
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
		
		List<GameState> l = new LinkedList();
		Collection<GameState> c = gameinteraction.getPlayersGames(username);
		Iterator iter = c.iterator();
		
		while(iter.hasNext()) {
			   l.add((GameState)iter.next());
		}
		
		return l;
	}

	public GameState makeMove(int gameID, int gameInstanceID, int gameStateID, String playerUID, List<Coordinate> coordList) {
		try
		{
			GameMove gamemove = new GameMove(gameID, gameInstanceID, gameStateID, playerUID);
			Iterator iter = coordList.iterator();
			while(iter.hasNext()) {
			   gamemove.addMoveCoordinate((Coordinate)iter.next());
			}
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
		finally{
			List<GameState> l = gamepersistence.getGameStates();
			Iterator li = l.iterator();
			while (li.hasNext()) {
				GameState gs = (GameState)li.next();
				if (gs.getGameInstanceID() == gameinstanceid) {
					return gs;
				}
			}
			return null;
		}
	}
	
	public boolean storeGameStates() {
		return gamepersistence.storeGameStates(gameinteraction.getAllActiveGames());
	}
	
	public boolean userAuthenticate(String username, String password) {
		return userauthentication.userauthenticate(username, password);
	}
	
	public void addUser(String username, String password) {
		userauthentication.adduser(username, password);
	}
	
}
