package org.swat.client.data;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataParsing
{
	// Writes an error message if the expression is false
	public static void verify(PrintWriter writer, boolean expression,
			String errorMessage)
	{
		if (!expression)
		{
			writer.println("ERROR: " + errorMessage);
		}
	}

	public static GameState readGameState(LineReader reader)
	{
		return null;
	}

	public static void writeGameState(PrintWriter writer, GameState state)
	{
	}

	public static GameInfo readGameInfo(LineReader reader)
	{
		GameInfo info = new GameInfo();

		// Verify the opening
		if (!reader.advance().equals("BEGIN_GAME_INFO"))
		{
			return null;
		}

		// Verify the name
		if (!reader.advance().startsWith("name="))
		{
			return null;
		}
		info.setGameName(reader.getLine().substring(5));

		// Get the piece types
		info.setPieceTypes(readStringIntMap(reader));

		// Verify the closing
		if (!reader.advance().equals("END_GAME_INFO"))
		{
			return null;
		}

		return info;
	}

	public static void writeGameInfo(PrintWriter writer, GameInfo info)
	{
		writer.println("BEGIN_GAME_INFO");
		writer.println("name=" + info.getGameName());
		writeStringIntMap(writer, info.getPieceTypes());
		writer.println("END_GAME_INFO");
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

	public static int[][] readGrid(LineReader reader)
	{
		int[][] grid;
		int width, height;
		String width_str, height_str;

		// Verify the opening
		if (!reader.advance().equals("BEGIN_GRID"))
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

		// Create the grid
		try
		{
			height = Integer.parseInt(height_str.substring(7));
			width = Integer.parseInt(width_str.substring(6));
			grid = new int[height][width];
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
					grid[i][j] = Integer.parseInt(elements[j]);
				}
				catch (NumberFormatException e)
				{
					return null;
				}
			}
		}

		// Verify the closing
		if (!reader.getLine().equals("END_GRID"))
		{
			return null;
		}

		return grid;
	}

	public static void writeGrid(PrintWriter writer, int[][] grid)
	{
		writer.println("BEGIN_GRID");
		writer.println("width=" + grid[0].length);
		writer.println("height=" + grid.length);
		for (int[] row : grid)
		{
			for (int i = 0; i < row.length - 1; i++)
			{
				writer.print(row[i] + ":");
			}
			writer.println(row[row.length - 1]);
		}
		writer.println("END_GRID");
	}
}
