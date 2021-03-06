package org.swat.client.communication;

import java.io.PrintWriter;
import java.util.List;

import org.swat.data.Coordinate;
import org.swat.data.DataParsing;
import org.swat.data.GameState;
import org.swat.data.IGameInfo;
import org.swat.data.LineReader;

/**
 * Provides access to the server's exported interface.
 * 
 * @author tombuzbee
 * 
 */
public class ServerInterface
{
	private static LineReader reader;
	private static PrintWriter writer;
	private static ServerConnection server;
	private static String username;
	private static String password;

	private static String serverIP = "127.0.0.1";
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
		if (reader.advance() == null)
		{
			System.err.println("ERROR: No response from server");
			return false;
		}
		if (reader.getLine().startsWith("ERROR"))
		{
			System.err.println(reader.getLine());
			return false;
		}
		if (!reader.getLine().equals("BEGIN_RESPONSE"))
		{
			System.err.println("ERROR: Invalid response from server");
			return false;
		}

		return true;
	}

	private static boolean verifyResponseClosing()
	{
		if (!reader.advance().equals("END_RESPONSE"))
		{
			System.err.println("ERROR: Invalid response from server");
			return false;
		}

		return true;
	}

	/**
	 * Sends the createGame command
	 * 
	 * @param gameType
	 * @return
	 */
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

	/**
	 * Sends the joinGame command
	 * 
	 * @param gameID
	 * @return
	 */
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

	/**
	 * Sends the makeMove command
	 * 
	 * @param gameID
	 * @param stateID
	 * @param coordList
	 * @return
	 */
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

	/**
	 * Sends the retrieveDeployedGames command
	 * 
	 * @return
	 */
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

	/**
	 * Sends the retrieveGameInfo command
	 * 
	 * @param gameType
	 * @return
	 */
	public static IGameInfo retrieveGameInfo(String gameType)
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
		IGameInfo info = DataParsing.readGameInfo(reader);
		if (!verifyResponseClosing())
			return null;

		disconnect();

		return info;
	}

	/**
	 * Sends the retrieveGameState command
	 * 
	 * @param gameID
	 * @return
	 */
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

	/**
	 * Sends the retrieveMyGames command
	 * 
	 * @return
	 */
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

	/**
	 * Sends the retrieveOpenGames command
	 * 
	 * @return
	 */
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

	/**
	 * @return
	 */
	public static String getServerIP()
	{
		return serverIP;
	}

	/**
	 * @param serverIP
	 */
	public static void setServerIP(String serverIP)
	{
		ServerInterface.serverIP = serverIP;
	}

	/**
	 * @return
	 */
	public static int getServerPort()
	{
		return serverPort;
	}

	/**
	 * @param serverPort
	 */
	public static void setServerPort(int serverPort)
	{
		ServerInterface.serverPort = serverPort;
	}

	/**
	 * @return
	 */
	public static String getUsername()
	{
		return username;
	}

	/**
	 * @param username
	 */
	public static void setUsername(String username)
	{
		ServerInterface.username = username;
	}

	/**
	 * @return
	 */
	public static String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 */
	public static void setPassword(String password)
	{
		ServerInterface.password = password;
	}
}
