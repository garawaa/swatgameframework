package org.swat.game;

import java.util.HashMap;

public interface Game {
		
	public abstract int getID();
	public String getName();
	public int getNumberOfPlayersNeeded();
	public int getBoardLength();
	public int getBoardWidth();
	public HashMap<Integer, String> getPieces();
	
	public GameState getInitialState();
	public boolean makeMove(GameState state, GameMove move);
	public abstract GAME_PLAYERS getPlayerWhoShouldMakeMove();

}
