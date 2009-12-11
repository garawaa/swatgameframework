package org.swat.server.game.interaction;

import java.util.Collection;

import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.data.IGameInfo;

/**
 * The main interaction point with the server. This class abstracts the 
 * details of the server from the game and the game from the server.
 * 
 * @author steve
 *
 */
public interface IGameInteraction {
	
	/**
	 * Retrieves all the games deployed on the server
	 * @return the list of human-readable game names that are deployed on the server
	 */
	public Collection<String> getDeployedGames();
	
	/**
	 * Returns the info about a particular game
	 * @param gameName the name of the game to get info on
	 * @return the information about the game, if it exists
	 */
	public IGameInfo getGameInfo(String gameName);

	/**
	 * Creates a new game
	 * @param gameID the UID of the game to create a new instance of
	 * @param playerUID the player UID who is creating the game instance
	 * @return the current state of the newly created game instance
	 */
	public GameState createGame(int gameID, String playerUID);
	
	/**
	 * Returns the list of all games currently in the CREATED state
	 * @return all the games that need more players
	 */
	public Collection<GameState> getGamesThatNeedPlayers();
	
	/**
	 * Makes a move on a game instance
	 * @param move the move to make
	 * @return the state of the game after the move, if valid, is made
	 */
	public GameState makeMove(GameMove move);
	
	/**
	 * Gets the current state of a game instance
	 * @param gameInstanceID the instance id of the game instances whose state is needed
	 * @return the requested game state, if it is a valid request
	 */
	public GameState getGameState(int gameInstanceID);
	
	/**
	 * Join a game instance
	 * @param gameInstanceID the ID of the game instance to join
	 * @param playerUID the UID of the requesting player
	 * @return the new gamestate after the join request, if valid, is processed
	 */
	public GameState joinGame(int gameInstanceID, String playerUID);
	
	/**
	 * Get the list of games in which a player is a participant
	 * @param playerUID the ID of the player whose games are needed
	 * @return all the players games (both CREATED and STARTED)
	 */
	public Collection<GameState> getPlayersGames(String playerUID);
	
	/**
	 * Gets the state of all the games currently on the server
	 * @return all gameStates currently on server
	 */
	public Collection<GameState> getAllActiveGames();

}
