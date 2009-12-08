package org.swat.server.game;

import org.swat.data.GameInfo;
import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;

public interface IGame {

	public GameState makeMove(GameState state, GameMove move)
			throws IllegalGameStateException, IllegalMoveException;
	
	public GameInfo getGameInfo();

}
