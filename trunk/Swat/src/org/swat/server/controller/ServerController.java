package org.swat.server.controller;

import java.util.Iterator;
import java.util.List;

import org.swat.data.Game;
import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;
import org.swat.server.gameinteraction.GameInteractionManager;

public class ServerController {
	
	private final GamePersistence gamepersistence;
	private final UserAuthentication userauthentication;
	private final GameInteractionManager gameinteraction;
	
	public ServerController() {
		gamepersistence = new GamePersistence();
		userauthentication = new UserAuthentication();
		gameinteraction = GameInteractionManager.getInstance();
	}
	
	public List<Game> retrieveDeployedGames() {
		return gameinteraction.getDeployedGames();
	}
	
	public List<GameState> retrieveOpenGames() {
		return gameinteraction.getGamesThatNeedPlayers();
	}
	
	//public retrieveGameInfo()
	
	//who create a game?
	public GameState createGame(int gameid, String username) {
		return gameinteraction.createGame(gameid, username);
	}
	
	//gameinstanceid ?
	public GameState joinGame(int gameid, String username) {
		return gameinteraction.joinGame(gameid, username);
	}
	
	public List<GameState> retrieveMyGame(String username) {
		return gameinteraction.getPlayersGames(username);
	}

	public GameState makeMove(GameMove gamemove) {
		try
		{
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
		GameState gamestate = gameinteraction.getGameState(gameinstanceid);
		if (gamestate == null) {
			List<GameState> l = gamepersistence.getGameStates();
			Iterator li = l.iterator();
			while (li.hasNext()) {
				GameState gs = (GameState)li.next();
				if (gs.getGameInstanceID() == gameinstanceid) {
					gamestate = gs;
				}
			}
		}
		return gamestate;
	}
	
	public boolean storeGameStates() {
		return gamepersistence.storeGameStates(gameinteraction.getAllActiveGames());
	}
	
	public boolean userAuthenticate(String username, String password) {
		return userauthentication.userauthenticate(username, password);
	}
	
}
