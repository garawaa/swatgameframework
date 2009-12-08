package org.swat.client.communication;

import java.io.PrintWriter;
import java.util.List;

import org.swat.data.Coordinate;
import org.swat.data.DataParsing;
import org.swat.data.GameInfo;
import org.swat.data.GameState;
import org.swat.data.LineReader;

public class ServerInterface
{
	private static LineReader reader;
	private static PrintWriter writer;
	private static ServerConnection server;
	private static String username;
	private static String password;

	private static String serverIP = "172.21.211.190";
	private static int serverPort = 9876;

	private static void connect()
	{
		server = new ServerConnection(serverIP, serverPort);
		reader = server.getReader();
		writer = server.getWriter();
	}

	private static void disconnect()
	{
		server.closeConnection();
		reader = null;
		writer = null;
	}

	private static void writeAuthenticationInfo()
	{
		writer.println("username:" + username);
		writer.println("password:" + password);
	}

	private static boolean verifyResponseOpening()
	{
		if (reader.advance().startsWith("ERROR"))
		{
			// TODO error("error");
			return false;
		}
		if (!reader.advance().equals("BEGIN_RESPONSE"))
		{
			// TODO error("Malformed response");
			return false;
		}

		return true;
	}

	private static boolean verifyResponseClosing()
	{
		if (!reader.advance().equals("END_RESPONSE"))
		{
			// TODO error("Malformed response");
			return false;
		}

		return true;
	}

	public static GameState createGame(String gameType)
	{
		connect();

		// Send request
		writeAuthenticationInfo();
		writer.println("createGame");
		writer.println(gameType);
		writer.println("END_REQUEST");

		// Read response
		if (!verifyResponseOpening())
			return null;
		GameState state = DataParsing.readGameState(reader);
		if (!verifyResponseClosing())
			return null;

		disconnect();

		return state;
	}

	public static GameState joinGame(int gameID)
	{
		connect();

		// Send request
		writeAuthenticationInfo();
		writer.println("joinGame");
		writer.println(gameID);
		writer.println("END_REQUEST");

		// Read response
		if (!verifyResponseOpening())
			return null;
		GameState state = DataParsing.readGameState(reader);
		if (!verifyResponseClosing())
			return null;

		disconnect();

		return state;
	}

	public static GameState makeMove(int gameID, int stateID,
			List<Coordinate> coordList)
	{
		connect();

		// Send request
		writeAuthenticationInfo();
		writer.println("makeMove");
		writer.println(gameID);
		writer.println(stateID);
		DataParsing.writeCoordinateList(writer, coordList);
		writer.println("END_REQUEST");

		// Read response
		if (!verifyResponseOpening())
			return null;
		GameState state = DataParsing.readGameState(reader);
		if (!verifyResponseClosing())
			return null;

		disconnect();

		return state;
	}

	public static List<String> retrieveDeployedGames()
	{
		connect();

		// Send request
		writeAuthenticationInfo();
		writer.println("retrieveDeployedGames");
		writer.println("END_REQUEST");

		// Read response
		if (!verifyResponseOpening())
			return null;
		List<String> games = DataParsing.readStringList(reader);
		if (!verifyResponseClosing())
			return null;

		disconnect();

		return games;
	}

	public static GameInfo retrieveGameInfo(String gameType)
	{
		connect();

		// Send request
		writeAuthenticationInfo();
		writer.println("retrieveGameInfo");
		writer.println(gameType);
		writer.println("END_REQUEST");

		// Read response
		if (!verifyResponseOpening())
			return null;
		GameInfo info = DataParsing.readGameInfo(reader);
		if (!verifyResponseClosing())
			return null;

		disconnect();

		return info;
	}

	public static GameState retrieveGameState(int gameID)
	{
		connect();

		// Send request
		writeAuthenticationInfo();
		writer.println("retrieveGameState");
		writer.println(gameID);
		writer.println("END_REQUEST");

		// Read response
		if (!verifyResponseOpening())
			return null;
		GameState state = DataParsing.readGameState(reader);
		if (!verifyResponseClosing())
			return null;

		disconnect();

		return state;
	}

	public static List<GameState> retrieveMyGames()
	{
		connect();

		// Send request
		writeAuthenticationInfo();
		writer.println("retrieveMyGames");
		writer.println("END_REQUEST");

		// Read response
		if (!verifyResponseOpening())
			return null;
		List<GameState> states = DataParsing.readGameStateList(reader);
		if (!verifyResponseClosing())
			return null;

		disconnect();

		return states;
	}

	public static List<GameState> retrieveOpenGames()
	{
		connect();

		// Send request
		writeAuthenticationInfo();
		writer.println("retrieveMyGames");
		writer.println("END_REQUEST");

		// Read response
		if (!verifyResponseOpening())
			return null;
		List<GameState> states = DataParsing.readGameStateList(reader);
		if (!verifyResponseClosing())
			return null;

		disconnect();

		return states;
	}

	public static String getServerIP()
	{
		return serverIP;
	}

	public static void setServerIP(String serverIP)
	{
		ServerInterface.serverIP = serverIP;
	}

	public static int getServerPort()
	{
		return serverPort;
	}

	public static void setServerPort(int serverPort)
	{
		ServerInterface.serverPort = serverPort;
	}

	public static String getUsername()
	{
		return username;
	}

	public static void setUsername(String username)
	{
		ServerInterface.username = username;
	}

	public static String getPassword()
	{
		return password;
	}

	public static void setPassword(String password)
	{
		ServerInterface.password = password;
	}
}
