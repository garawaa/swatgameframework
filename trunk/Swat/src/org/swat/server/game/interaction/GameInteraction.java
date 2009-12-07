package org.swat.server.game.interaction;

import java.util.Collection;
import java.util.List;

import org.swat.data.GameInfo;
import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.server.game.exceptions.GameNotFoundException;
import org.swat.server.game.exceptions.IllegalGameJoinException;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;


public interface GameInteraction {
	
	/*
	 * Provides a list of Game objects, whose 
	 * getName() provides the string name of that
	 * game, and getID() provides the UI for that
	 * game type
	 */
	public Collection<String> getDeployedGames();
	
	public GameInfo getGameInfo(String gameName) throws GameNotFoundException;
	
	/*
	 * Use ID obtained from getDeployedGames()
	 */
	public GameState createGame(int gameID, String playerUID);
	
	public Collection<GameState> getGamesThatNeedPlayers();
	
	
	public GameState makeMove(GameMove move) throws IllegalMoveException, IllegalGameStateException;
	
	public GameState getGameState(int gameInstanceID) throws IllegalGameStateException;
	
	public GameState joinGame(int gameInstanceID, String playerUID) throws IllegalGameJoinException;
	
	/*
	 * Retrieves the list of games for which the player
	 * is the owner
	 */
	public Collection<GameState> getPlayersGames(String playerUID);
	
	/*
	 * For persistence
	 */
	public Collection<GameState> getAllActiveGames();

}
