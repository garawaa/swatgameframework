package org.swat.server.gameinteraction;

import java.util.Collection;
import java.util.List;

import org.swat.data.GameMove;
import org.swat.server.game.Game;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;
import org.swat.data.GameState;


public interface GameInteraction {
	
	/*
	 * Provides a list of Game objects, whose 
	 * getName() provides the string name of that
	 * game, and getID() provides the UI for that
	 * game type
	 */
	public Collection<Game> getDeployedGames();
	
	/*
	 * Use ID obtained from getDeployedGames()
	 */
	public GameState createGame(int gameID, String playerUID);
	
	public List<GameState> getGamesThatNeedPlayers();
	
	
	public GameState makeMove(GameMove move) throws IllegalMoveException, IllegalGameStateException;
	
	public GameState getGameState(int gameInstanceID);
	
	public GameState joinGame(int gameInstanceID, String playerUID);
	
	/*
	 * Retrieves the list of games for which the player
	 * is the owner
	 */
	public List<GameState> getPlayersGames(String playerUID);
	
	/*
	 * For persistence
	 */
	public Collection<GameState> getAllActiveGames();

}
