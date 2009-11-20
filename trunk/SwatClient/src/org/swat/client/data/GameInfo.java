package org.swat.client.data;

import java.util.Map;

public class GameInfo
{
	private String gameName;
	private Map<String, Integer> pieceTypes;

	public GameInfo(String s, Map<String, Integer> map)
	{
		this.gameName = s;
		this.pieceTypes  = map;
	}
	
	public GameInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getGameName()
	{
		return gameName;
	}

	public Map<String, Integer> getPieceTypes()
	{
		return pieceTypes;
	}

	public void setGameName(String gameName)
	{
		this.gameName = gameName;
	}

	public void setPieceTypes(Map<String, Integer> pieceTypes)
	{
		this.pieceTypes = pieceTypes;
	}
}
