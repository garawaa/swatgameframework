package org.swat.data;

import java.util.Map;

public class GameInfo
{
	private String gameName;
	private Map<String, Integer> pieceTypes;

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
