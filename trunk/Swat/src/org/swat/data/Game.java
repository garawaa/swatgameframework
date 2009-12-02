package org.swat.data;

import java.util.HashMap;

import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;

public interface Game {

	public abstract int getID();

	public String getName();

	public GAME_TYPE getGameType();

	public int getNumberOfPlayersNeeded();

	public int getBoardLength();

	public int getBoardWidth();

	public HashMap<Integer, String> getPieces();

	public GameState getInitialState();

	public GameState makeMove(GameState state, GameMove move)
			throws IllegalGameStateException, IllegalMoveException;

}
