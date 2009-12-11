package org.swat.data;

import java.util.Map;

/**
 * The interface that is implemented by the GameInfo objects. This interface
 * details the mandatory information that is to be provided by each GameInfo 
 * object.
 * 
 * @author steve
 *
 */
public interface IGameInfo {
	
	/**
	 * Get the human-readable name of this game
	 * @return the human-readable name of this game
	 */
	public String getGameName();
	
	/**
	 * Get the UID of this game
	 * @return the UID of this game
	 */
	public int getGameID();
	
	/**
	 * Get the board state of this game
	 * @return the current board state of this game
	 */
	public Map<Integer, String> getPieces();
	
	/**
	 * Get the type of this game
	 * @return the type of this game
	 */
	public GAME_TYPE getGameType();

	/**
	 * Get the number of players need to start an instance of this game
	 * @return the number of players needed to start an instance of this game
	 */
	public int getNumPlayersNeeded();
	
	/**
	 * Get the length of the board for this game
	 * @return the length of the board for this game
	 */
	public int getBoardLength();
	
	/**
	 * Get the width of the board for this game
	 * @return the width of the board for this game
	 */
	public int getBoardWidth();
	
	/**
	 * Get the initial state of this game
	 * @return the initial state of this game
	 */
	public GameState getInitialState();

}
