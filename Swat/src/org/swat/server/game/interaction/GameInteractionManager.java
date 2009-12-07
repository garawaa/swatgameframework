package org.swat.server.game.interaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.swat.data.GAME_STATE;
import org.swat.data.GameInfo;
import org.swat.data.GameMove;
import org.swat.server.game.Game;
import org.swat.server.game.exceptions.GameNotFoundException;
import org.swat.server.game.exceptions.IllegalGameJoinException;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;
import org.swat.server.game.impl.TicTacToe;
import org.swat.data.GameState;

public class GameInteractionManager implements GameInteraction {

	private static GameInteractionManager _instance;
	private static HashMap<Integer, Game> games;
	
	private HashMap<Integer, GameState> createdGames;
	private static HashMap<Integer, GameState> startedGames;
	private HashMap<String, Collection<GameState>> gamesByPlayer;

	private GameInteractionManager() {

		startedGames = new HashMap<Integer, GameState>();
		games = new HashMap<Integer, Game>();
		createdGames = new HashMap<Integer, GameState>();
		gamesByPlayer = new HashMap<String, Collection<GameState>>();

		//IMPROVE: game finding and addition
		Game newGame = TicTacToe.getLogic();
		games.put(newGame.getID(), newGame);
		
	}

	public synchronized void deployGame(Game game) {

		games.put(game.getID(), game);

	}

	public static synchronized GameInteractionManager getInstance() {

		if (_instance == null)
			_instance = new GameInteractionManager();

		return _instance;

	}

	@Override
	public GameState createGame(int gameID, String playerUID) {

		Game gameToCreate = games.get(gameID);
		GameState initialGameState = gameToCreate.getInitialState();
		initialGameState.addPlayer(playerUID);
		
		if(!gamesByPlayer.containsKey(playerUID))
			gamesByPlayer.put(playerUID, new ArrayList<GameState>());
		gamesByPlayer.get(playerUID).add(initialGameState);
		
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
		
		for(Game game : games.values())
			deployedGames.add(game.getName());
			
		return deployedGames;

	}
	
	@Override
	public GameInfo getGameInfo(String gameName) throws GameNotFoundException {
		
		for(Game game : games.values())
			if(gameName.equals(game.getName()))
				return game.getGameInfo();
		
		throw new GameNotFoundException();
		
	}

	@Override
	public GameState getGameState(int gameInstanceID) throws IllegalGameStateException {

		if(startedGames.containsKey(gameInstanceID))
			return (startedGames.get(gameInstanceID).clone());
		if(createdGames.containsKey(gameInstanceID))
			return (createdGames.get(gameInstanceID).clone());
		
		throw (new IllegalGameStateException());
		
	}

	@Override
	public Collection<GameState> getGamesThatNeedPlayers() {
		return createdGames.values();
	}

	@Override
	public Collection<GameState> getPlayersGames(String playerUID) {
		return gamesByPlayer.get(playerUID);
	}

	@Override
	public GameState joinGame(int gameInstanceID, String playerUID) throws IllegalGameJoinException {
		
		/*
		 * if the game is not in the created state, throw exception
		 * else, move it from created to active, add player, 
		 * store in players games, store in started games, return 
		 * state
		 */
		
		if(!createdGames.containsKey(gameInstanceID))
			throw (new IllegalGameJoinException());
		
		GameState requestedGameState = createdGames.get(gameInstanceID);
		requestedGameState.addPlayer(playerUID);
		requestedGameState.setGameState(GAME_STATE.STARTED);
		createdGames.remove(gameInstanceID);
		for(String players: requestedGameState.getPlayers())
			gamesByPlayer.get(players).add(requestedGameState);
		
		startedGames.put(gameInstanceID, requestedGameState);
			
		return requestedGameState.clone();
		
	}

	@Override
	public GameState makeMove(GameMove move) throws IllegalMoveException,
			IllegalGameStateException {

		/* TODO
		 * if game is not started, exception
		 * if game counter does not match, exception
		 * synchronize
		 * store new state in gamestates as well as players games
		 */
		
		Game specifiedGame = games.get(move.getGameID());
		GameState specifiedGameState = startedGames.get(move.getGameInstanceID());
		if (specifiedGameState.getCounter() != move.getCounter())
			throw new IllegalMoveException();

		return (specifiedGame.makeMove(specifiedGameState, move).clone());

	}

}
