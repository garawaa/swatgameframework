package org.swat.server.gameinteraction;

import java.util.List;

import org.swat.data.GameMove;
import org.swat.server.game.Game;
import org.swat.data.GameState;

public class GameInteractionManager implements GameInteraction {

	private static GameInteractionManager _instance;
	
	private GameInteractionManager() {
		
	}
	
	public static synchronized GameInteractionManager getInstance() {
		
		if(_instance == null)
			_instance = new GameInteractionManager();
		
		return _instance;
		
	}
	
	
	@Override
	public GameState createGame(int gameID, String playerUID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GameState> getAllActiveGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> getDeployedGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameState getGameState(int gameInstanceID) {
		// TODO Auto-generated method stub
		return null;
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
	public GameState makeMove(GameMove move) {
		// TODO Auto-generated method stub
		return null;
	}

}
