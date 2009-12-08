package org.swat.server.game;

import java.util.Map;

import org.swat.data.GAME_TYPE;
import org.swat.data.GameState;

public interface IGameInfo {
	
	public String getGameName();
	
	public int getGameID();
	
	public Map<Integer, String> getPieces();
	
	public GAME_TYPE getGameType();

	public int getNumPlayersNeeded();
	
	public int getBoardLength();
	
	public int getBoardWidth();
	
	public GameState getInitialState();

}
