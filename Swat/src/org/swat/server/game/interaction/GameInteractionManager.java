package org.swat.server.game.interaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.swat.data.GAME_STATE;
import org.swat.data.GameInfo;
import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.data.MESSAGE;
import org.swat.server.game.IGame;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;
import org.swat.server.game.impl.TicTacToe;

public class GameInteractionManager implements IGameInteraction {

	private static GameInteractionManager _instance;
	private static HashMap<Integer, IGame> games;
	
	private static HashMap<Integer, GameState> createdGames;
	private static HashMap<Integer, GameState> startedGames;
	private static HashMap<String, Collection<Integer>> gamesByPlayer;

	private GameInteractionManager() {

		startedGames = new HashMap<Integer, GameState>();
		games = new HashMap<Integer, IGame>();
		createdGames = new HashMap<Integer, GameState>();
		gamesByPlayer = new HashMap<String, Collection<Integer>>();

		//IMPROVE: game finding and addition
		IGame newGame = TicTacToe.getLogic();
		games.put(newGame.getGameInfo().getGameID(), newGame);
		
	}

	public synchronized void deployGame(IGame game) {

		games.put(game.getGameInfo().getGameID(), game);

	}

	public static synchronized IGameInteraction getInstance() {

		if (_instance == null)
			_instance = new GameInteractionManager();

		return _instance;

	}

	@Override
	public GameState createGame(int gameID, String playerUID) {

		if(!games.containsKey(gameID)) {
			GameState tempGameState = new GameState();
			tempGameState.addMessage(MESSAGE.ILLEGAL_GAME_SPEC.toString());
			return (tempGameState);
		}
		
		IGame gameToCreate = games.get(gameID);
		GameState initialGameState = gameToCreate.getGameInfo().getInitialState();
		initialGameState.setGameID(gameID);
		initialGameState.setNumberOfPlayersNeeded(gameToCreate.getGameInfo().getNumPlayersNeeded());
		initialGameState.addPlayer(playerUID);
		initialGameState.instantiate();
		
		if(!gamesByPlayer.containsKey(playerUID))
			gamesByPlayer.put(playerUID, new ArrayList<Integer>());
		gamesByPlayer.get(playerUID).add(initialGameState.getGameInstanceID());
		
		createdGames.put(initialGameState.getGameInstanceID(), initialGameState);
		
		return (initialGameState.clone());

	}

	@Override
	public List<GameState> getAllActiveGames() {

		return new ArrayList<GameState>(startedGames.values());

	}
	
	@Override
	public Collection<String> getDeployedGames() {

		Collection<String> deployedGames = new ArrayList<String>();
		
		for(IGame game : games.values())
			deployedGames.add(game.getGameInfo().getGameName());
			
		return deployedGames;

	}
	
	@Override
	public GameInfo getGameInfo(String gameName) {
		
		for(IGame game : games.values())
			if(gameName.equals(game.getGameInfo().getGameName()))
				return game.getGameInfo();
		
		GameInfo tempGameInfo = new GameInfo();
		tempGameInfo.setGameID(-1);
		return (tempGameInfo);
		
	}

	@Override
	public GameState getGameState(int gameInstanceID) {

		if(startedGames.containsKey(gameInstanceID))
			return (startedGames.get(gameInstanceID).clone());
		if(createdGames.containsKey(gameInstanceID))
			return (createdGames.get(gameInstanceID).clone());
		
		GameState tempGameState = new GameState();
		tempGameState.addMessage(MESSAGE.ILLEGAL_GAME_STATE.toString());
		return (tempGameState);
				
	}

	@Override
	public Collection<GameState> getGamesThatNeedPlayers() {
		return createdGames.values();
	}

	@Override
	public Collection<GameState> getPlayersGames(String playerUID) {
		Collection<GameState> playersGames = new ArrayList<GameState>();
		
		if(!gamesByPlayer.containsKey(playerUID))
			return(playersGames);
		
		for(Integer gameStateID : gamesByPlayer.get(playerUID)) {
			if(createdGames.containsKey(gameStateID))
				playersGames.add(createdGames.get(gameStateID));
			else
				playersGames.add(startedGames.get(gameStateID));
		}
		return(playersGames);
	}

	@Override
	public GameState joinGame(int gameInstanceID, String playerUID) {
		
		/*
		 * if the game is not in the created state, throw exception
		 * else, move it from created to active, add player, 
		 * store in players games, store in started games, return 
		 * state
		 */
		
		if(!createdGames.containsKey(gameInstanceID)){
			GameState tempGameState = new GameState();
			tempGameState.addMessage(MESSAGE.ILLEGAL_GAME_JOIN.toString());
			return (tempGameState);
		}
		
		GameState requestedGameState = createdGames.get(gameInstanceID);
		// TODO Game should only transition to STARTED state if enough players
		// have joined
		requestedGameState.addPlayer(playerUID);
		requestedGameState.setGameState(GAME_STATE.STARTED);
		createdGames.remove(gameInstanceID);
		
		for(String player: requestedGameState.getPlayers()) {
			if(!gamesByPlayer.containsKey(player))
				gamesByPlayer.put(player, new ArrayList<Integer>());
			gamesByPlayer.get(player).add(requestedGameState.getGameInstanceID());
		}
		
		startedGames.put(gameInstanceID, requestedGameState);
			
		return requestedGameState.clone();
		
	}

	@Override
	public GameState makeMove(GameMove move) {

		GameState tempGameState = new GameState();
		
		if(!startedGames.containsKey(move.getGameInstanceID())){
			tempGameState.addMessage(MESSAGE.ILLEGAL_GAME_STATE.toString());
			return (tempGameState);
		}
		
		GameState specifiedGameState = startedGames.get(move.getGameInstanceID());
		IGame specifiedGame = games.get(specifiedGameState.getGameID());
		
		GameState newGameState = null;
		synchronized(this) {
			
			try {
				newGameState = specifiedGame.makeMove(specifiedGameState, move);
			} catch (IllegalGameStateException e) {
				tempGameState.addMessage(MESSAGE.ILLEGAL_GAME_STATE.toString());
				return (tempGameState);
			} catch (IllegalMoveException e) {
				tempGameState.addMessage(MESSAGE.ILLEGAL_GAME_MOVE.toString());
				return (tempGameState);
			}
			startedGames.put(newGameState.getGameInstanceID(), newGameState);
				
		}
		
		return (newGameState.clone());

	}

}
