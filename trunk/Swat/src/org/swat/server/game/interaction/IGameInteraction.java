package org.swat.server.game.interaction;

import java.util.Collection;

import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.data.IGameInfo;


public interface IGameInteraction {
	
	/*
	 * Provides a list of Game objects, whose 
	 * getName() provides the string name of that
	 * game, and getID() provides the UI for that
	 * game type
	 */
	public Collection<String> getDeployedGames();
	
	public IGameInfo getGameInfo(String gameName);
	
	/*
	 * Use ID obtained from getDeployedGames()
	 */
	public GameState createGame(int gameID, String playerUID);
	
	public Collection<GameState> getGamesThatNeedPlayers();
	
	
	public GameState makeMove(GameMove move);
	
	public GameState getGameState(int gameInstanceID);
	
	public GameState joinGame(int gameInstanceID, String playerUID);
	
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