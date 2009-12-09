package org.swat.data;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataParsing
{
	public static IGameInfo readGameInfo(LineReader reader)
	{
		String gameID, gameName, gameType, numPlayersNeeded, boardLength, boardWidth;
		GameState initialState;
		Map<Integer, String> pieces;

		// Verify the opening
		if (!reader.advance().equals("BEGIN_GAME_INFO"))
		{
			return null;
		}

		// Get the gameID value
		if (!(gameID = reader.advance()).startsWith("gameID="))
		{
			return null;
		}
		gameID = gameID.substring(7);

		// Get the gameName value
		if (!(gameName = reader.advance()).startsWith("gameName="))
		{
			return null;
		}
		gameName = gameName.substring(9);

		// Get the gameType value
		if (!(gameType = reader.advance()).startsWith("gameType="))
		{
			return null;
		}
		gameType = gameType.substring(9);

		// Get the numPlayersNeeded value
		if (!(numPlayersNeeded = reader.advance())
				.startsWith("numPlayersNeeded="))
		{
			return null;
		}
		numPlayersNeeded = numPlayersNeeded.substring(17);

		// Get the boardLength value
		if (!(boardLength = reader.advance()).startsWith("boardLength="))
		{
			return null;
		}
		boardLength = boardLength.substring(12);

		// Get the boardWidth value
		if (!(boardWidth = reader.advance()).startsWith("boardWidth="))
		{
			return null;
		}
		boardWidth = boardWidth.substring(11);

		// Get the remaining structures
		initialState = readGameState(reader);
		pieces = readIntStringMap(reader);

		// Verify the closing
		if (!reader.advance().equals("END_GAME_INFO"))
		{
			return null;
		}

		// Create a GameInfo from the strings
		try
		{
			GameInfo info = new GameInfo();
			info.setGameID(Integer.parseInt(gameID));
			info.setGameName(gameName);
			info.setGameType(GAME_TYPE.valueOf(gameType));
			info.setNumPlayersNeeded(Integer.parseInt(numPlayersNeeded));
			info.setBoardLength(Integer.parseInt(boardLength));
			info.setBoardWidth(Integer.parseInt(boardWidth));
			info.setInitialState(initialState);
			info.setPieces(pieces);
			return info;
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}

	public static void writeGameInfo(PrintWriter writer, IGameInfo info)
	{
		writer.println("BEGIN_GAME_INFO");
		writer.println("gameID=" + info.getGameID());
		writer.println("gameName=" + info.getGameName());
		writer.println("gameType=" + info.getGameType().name());
		writer.println("numPlayersNeeded=" + info.getNumPlayersNeeded());
		writer.println("boardLength=" + info.getBoardLength());
		writer.println("boardWidth=" + info.getBoardWidth());
		writeGameState(writer, info.getInitialState());
		writeIntStringMap(writer, info.getPieces());
		writer.println("END_GAME_INFO");
	}

	public static GameState readGameState(LineReader reader)
	{
		int[][] pieceInfo;
		String counter, gameInstanceID, gameState, turnOfPlayer, winnerID;
		List<String> messages, players;

		// Verify the opening
		if (!reader.advance().equals("BEGIN_GAME_STATE"))
		{
			return null;
		}

		// Get the counter value
		if (!(counter = reader.advance()).startsWith("counter="))
		{
			return null;
		}
		counter = counter.substring(8);

		// Get the gameInstanceID value
		if (!(gameInstanceID = reader.advance()).startsWith("gameInstanceID="))
		{
			return null;
		}
		gameInstanceID = gameInstanceID.substring(15);

		// Get the array of pieces
		pieceInfo = read2DIntArray(reader);

		// Get the gameState value
		if (!(gameState = reader.advance()).startsWith("gameState="))
		{
			return null;
		}
		gameState = gameState.substring(10);

		// Get the turnOfPlayer value
		if (!(turnOfPlayer = reader.advance()).startsWith("turnOfPlayer="))
		{
			return null;
		}
		turnOfPlayer = turnOfPlayer.substring(13);

		// Get the winnerID value
		if (!(winnerID = reader.advance()).startsWith("winnerID="))
		{
			return null;
		}
		winnerID = winnerID.substring(9);

		// Get the two lists
		messages = readStringList(reader);
		players = readStringList(reader);

		// Verify the closing
		if (!reader.advance().equals("END_GAME_STATE"))
		{
			return null;
		}

		// Create a GameState from the values
		try
		{
			GameState state = new GameState();
			state.setCounter(Integer.parseInt(counter));
			state.setGameInstanceID(Integer.parseInt(gameInstanceID));
			state.setPieceInfo(pieceInfo);
			state.setGameState(GAME_STATE.valueOf(gameState));
			state.setTurnOfPlayer(turnOfPlayer);
			state.setWinnerID(winnerID);
			state.setMessages(messages);
			state.setPlayers(players);
			return state;
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}

	public static void writeGameState(PrintWriter writer, GameState state)
	{
		writer.println("BEGIN_GAME_STATE");
		writer.println("counter=" + state.getCounter());
		writer.println("gameInstanceID=" + state.getGameInstanceID());
		write2DIntArray(writer, state.getPieceInfo());
		writer.println("gameState=" + state.getGameState().name());
		writer.println("turnOfPlayer=" + state.getTurnOfPlayer());
		writer.println("winnerID=" + state.getWinnerID());
		writeStringList(writer, state.getMessages());
		writeStringList(writer, state.getPlayers());
		writer.println("END_GAME_STATE");
	}

	public static List<GameState> readGameStateList(LineReader reader)
	{
		List<GameState> stateList = new ArrayList<GameState>();
		GameState state;

		// Verify the opening
		if (!reader.advance().equals("BEGIN_GAME_STATE_LIST"))
		{
			return null;
		}

		// Read each state and add to the list
		while ((state = readGameState(reader)) != null)
		{
			stateList.add(state);
		}

		// Verify the closing
		if (!reader.getLine().equals("END_GAME_STATE_LIST"))
		{
			return null;
		}

		return stateList;
	}

	public static void writeGameStateList(PrintWriter writer,
			List<GameState> stateList)
	{
		writer.println("BEGIN_GAME_STATE_LIST");
		for (GameState state : stateList)
		{
			writeGameState(writer, state);
		}
		writer.println("END_GAME_STATE_LIST");
	}

	public static Coordinate readCoordinate(LineReader reader)
	{
		String x, y;

		// Verify the opening
		if (!reader.advance().equals("BEGIN_COORDINATE"))
		{
			return null;
		}

		// Verify the x coordinate
		if (!(x = reader.advance()).startsWith("x="))
		{
			return null;
		}

		// Verify the y coordinate
		if (!(y = reader.advance()).startsWith("y="))
		{
			return null;
		}

		// Verify the closing
		if (!reader.advance().equals("END_COORDINATE"))
		{
			return null;
		}

		// Create a coordinate object from the strings
		try
		{
			return new Coordinate(Integer.parseInt(x.substring(2)), Integer
					.parseInt(y.substring(2)));
		}
		catch (NumberFormatException e)
		{
			return null;
		}
	}

	public static void writeCoordinate(PrintWriter writer, Coordinate coord)
	{
		writer.println("BEGIN_COORDINATE");
		writer.println("x=" + coord.getX());
		writer.println("y=" + coord.getY());
		writer.println("END_COORDINATE");
	}

	public static List<Coordinate> readCoordinateList(LineReader reader)
	{
		List<Coordinate> list = new ArrayList<Coordinate>();
		Coordinate coord;

		// Verify the opening
		if (!reader.advance().equals("BEGIN_COORDINATE_LIST"))
		{
			return null;
		}

		// Read each coordinate and add to the list
		while ((coord = readCoordinate(reader)) != null)
		{
			list.add(coord);
		}

		// Verify the closing
		if (!reader.getLine().equals("END_COORDINATE_LIST"))
		{
			return null;
		}

		return list;
	}

	public static void writeCoordinateList(PrintWriter writer,
			List<Coordinate> coordList)
	{
		writer.println("BEGIN_COORDINATE_LIST");
		for (Coordinate coord : coordList)
		{
			writeCoordinate(writer, coord);
		}
		writer.println("END_COORDINATE_LIST");
	}

	public static List<String> readStringList(LineReader reader)
	{
		List<String> list = new ArrayList<String>();

		// Verify the opening
		if (!reader.advance().equals("BEGIN_STRING_LIST"))
		{
			return null;
		}

		// Add each line to the list
		while (reader.advance() != null
				&& !reader.getLine().equals("END_STRING_LIST"))
		{
			list.add(reader.getLine());
		}

		// Verify the closing
		if (!reader.getLine().equals("END_STRING_LIST"))
		{
			return null;
		}

		return list;
	}

	public static void writeStringList(PrintWriter writer, List<String> list)
	{
		writer.println("BEGIN_STRING_LIST");
		for (String item : list)
		{
			writer.println(item);
		}
		writer.println("END_STRING_LIST");
	}

	public static Map<Integer, String> readIntStringMap(LineReader reader)
	{
		Map<Integer, String> map = new HashMap<Integer, String>();

		// Verify the opening
		if (!reader.advance().equals("BEGIN_INT_STRING_MAP"))
		{
			return null;
		}

		// Add each key-value pair to the map
		while (reader.advance() != null
				&& !reader.getLine().equals("END_INT_STRING_MAP"))
		{
			try
			{
				int separator = reader.getLine().indexOf(':');
				map.put(Integer.parseInt(reader.getLine().substring(0,
						separator)), reader.getLine().substring(separator + 1));
			}
			catch (NumberFormatException e)
			{
				return null;
			}
		}

		// Verify the closing
		if (!reader.getLine().equals("END_INT_STRING_MAP"))
		{
			return null;
		}

		return map;
	}

	public static void writeIntStringMap(PrintWriter writer,
			Map<Integer, String> map)
	{
		writer.println("BEGIN_INT_STRING_MAP");
		for (int key : map.keySet())
		{
			writer.println(key + ":" + map.get(key));
		}
		writer.println("END_INT_STRING_MAP");
	}

	public static Map<String, Integer> readStringIntMap(LineReader reader)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();

		// Verify the opening
		if (!reader.advance().equals("BEGIN_STRING_INT_MAP"))
		{
			return null;
		}

		// Add each key-value pair to the map
		while (reader.advance() != null
				&& !reader.getLine().equals("END_STRING_INT_MAP"))
		{
			try
			{
				int separator = reader.getLine().indexOf(':');
				map.put(reader.getLine().substring(0, separator), Integer
						.parseInt(reader.getLine().substring(separator + 1)));
			}
			catch (NumberFormatException e)
			{
				return null;
			}
		}

		// Verify the closing
		if (!reader.getLine().equals("END_STRING_INT_MAP"))
		{
			return null;
		}

		return map;
	}

	public static void writeStringIntMap(PrintWriter writer,
			Map<String, Integer> map)
	{
		writer.println("BEGIN_STRING_INT_MAP");
		for (String key : map.keySet())
		{
			writer.println(key + ":" + map.get(key));
		}
		writer.println("END_STRING_INT_MAP");
	}

	public static int[][] read2DIntArray(LineReader reader)
	{
		int[][] array;
		int width, height;
		String width_str, height_str;

		// Verify the opening
		if (!reader.advance().equals("BEGIN_2D_INT_ARRAY"))
		{
			return null;
		}

		// Verify the width
		if (!(width_str = reader.advance()).startsWith("width="))
		{
			return null;
		}

		// Verify the height
		if (!(height_str = reader.advance()).startsWith("height="))
		{
			return null;
		}

		// Create the array
		try
		{
			height = Integer.parseInt(height_str.substring(7));
			width = Integer.parseInt(width_str.substring(6));
			array = new int[height][width];
		}
		catch (NumberFormatException e)
		{
			return null;
		}

		// Read each element
		for (int i = 0; i < height; i++)
		{
			String[] elements = reader.advance().split(":");
			for (int j = 0; j < width; j++)
			{
				try
				{
					array[i][j] = Integer.parseInt(elements[j]);
				}
				catch (NumberFormatException e)
				{
					return null;
				}
			}
		}

		// Verify the closing
		if (!reader.advance().equals("END_2D_INT_ARRAY"))
		{
			return null;
		}

		return array;
	}

	public static void write2DIntArray(PrintWriter writer, int[][] array)
	{
		writer.println("BEGIN_2D_INT_ARRAY");
		writer.println("width=" + array[0].length);
		writer.println("height=" + array.length);
		for (int[] row : array)
		{
			for (int i = 0; i < row.length - 1; i++)
			{
				writer.print(row[i] + ":");
			}
			writer.println(row[row.length - 1]);
		}
		writer.println("END_2D_INT_ARRAY");
	}
}
