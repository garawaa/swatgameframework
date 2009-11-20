package org.swat.server.controller;

import java.util.*;
import java.io.*;

public class ServerController {
	
	private GamePersistence gamepersistence;
	private UserAuthentication userauthentication;
	private GameInteraction gameinteraction;
	
	public ServerController() {
		gamepersistence = new GamePersistence();
		userauthentication = new UserAuthentication();
		gameinteraction = new GameInteraction();
	}
	
	public List<GameState> retrieveDeployedGames() {
		return gameinteraction.getDeployedGames();
	}
	
	public List<GameState> retrieveOpenGames() {
		return gameinteraction.getGamesThatNeedPlayers();
	}
	
	//public retrieveGameInfo()
	
	//who create a game?
	public GameState createGame(int gameid, String username) {
		return gameinteraction.createGame(gameid);
	}
	
	//gameinstanceid ?
	public GameState joinGame(int gameid, String username) {
		return gameinteraction.joinGame(gameid, username);
	}
	
	public List<GameState> retrieveMyGame(String username) {
		return gameinteraction.getPlayersGames(username);
	}

	public GameState makeMove(GameMove gamemove) {
		return gameinteraction.makeMove(gamemove);
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
