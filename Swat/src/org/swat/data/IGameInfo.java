package org.swat.data;

import java.util.Map;


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
