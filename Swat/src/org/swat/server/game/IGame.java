package org.swat.server.game;

import org.swat.data.GameInfo;
import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;

/**
 * The interface to be implemented by every game
 * 
 * @author steve
 *
 */
public interface IGame {

	/**
	 * The main method of a game. This method must be re-entrant. 
	 * This method must not save any local state.
	 * 
	 * @param state the current state of the game
	 * @param move the move to make on the current state
	 * @return the new state of the game, after a valid move is made
	 * @throws IllegalGameStateException
	 * @throws IllegalMoveException
	 */
	public GameState makeMove(GameState state, GameMove move)
			throws IllegalGameStateException, IllegalMoveException;
	
	/**
	 * Get the information about this game
	 * @return the information about this game
	 */
	public GameInfo getGameInfo();

}
