package org.swat.server.game.interaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.swat.data.GameMove;
import org.swat.server.game.Game;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;
import org.swat.data.GameState;

public class GameInteractionManager implements GameInteraction {

	private static int gameInstanceIDCounter = 0;

	private static GameInteractionManager _instance;
	private static HashMap<Integer, GameState> gameStates;
	private static HashMap<Integer, Game> games;

	private GameInteractionManager() {

		// TODO:
		// enumerate all games
		// initialize gameStates, games

	}

	// add new game
	public synchronized void deployGame(Game game) {

		// TODO:

	}

	public static synchronized GameInteractionManager getInstance() {

		if (_instance == null)
			_instance = new GameInteractionManager();

		return _instance;

	}

	@Override
	public GameState createGame(int gameID, String playerUID) {

		// TODO: create new gamestate and return it, store it too
		return null;

	}

	@Override
	public List<GameState> getAllActiveGames() {

		// return the collection of gamestates
		return new ArrayList<GameState>(gameStates.values());

	}

	@Override
	public List<Game> getDeployedGames() {

		// return the collection of games
		return new ArrayList<Game>(games.values());

	}

	@Override
	public GameState getGameState(int gameInstanceID) {

		return gameStates.get(gameInstanceID);

	}

	@Override
	public List<GameState> getGamesThatNeedPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameState> getPlayersGames(String playerUID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameState joinGame(int gameInstanceID, String playerUID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameState makeMove(GameMove move) throws IllegalMoveException,
			IllegalGameStateException {

		Game specifiedGame = games.get(move.getGameID());
		GameState specifiedGameState = gameStates.get(move.getGameInstanceID());
		if (specifiedGameState.getCounter() != move.getCounter())
			throw new IllegalMoveException();

		return specifiedGame.makeMove(specifiedGameState, move);

	}

}
