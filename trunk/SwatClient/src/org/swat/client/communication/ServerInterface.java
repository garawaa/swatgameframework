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

	private static String serverIP = "172.21.123.94";
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
		// TODO
		writer.println("username:username");
		writer.println("password:password");
	}

	private static void verifyResponseOpening()
	{
		if (!reader.advance().equals("BEGIN_RESPONSE"))
		{
			// TODO error("Malformed response");
		}
		else if (reader.getLine().startsWith("ERROR"))
		{
			// TODO error("error");
		}
	}

	private static void verifyResponseClosing()
	{
		if (!reader.advance().equals("END_RESPONSE"))
		{
			// TODO error("Malformed response");
		}
	}

	public static GameState createGame(String gameType)
	{
		return null;
	}

	public static GameState joinGame(int gameID)
	{
		return null;
	}

	public static GameState makeMove(List<Coordinate> coordinates)
	{
		return null;
	}

	public static List<String> retrieveDeployedGames()
	{
		connect();

		// Send request
		writeAuthenticationInfo();
		writer.println("retrieveDeployedGames");
		writer.println("END_REQUEST");

		// Read response
		verifyResponseOpening();
		List<String> games = DataParsing.readStringList(reader);
		verifyResponseClosing();

		disconnect();

		return games;
	}

	public static GameInfo retrieveGameInfo(String gameType)
	{
		return null;
	}

	public static GameState retrieveGameState(int gameID)
	{
		return null;
	}

	public static List<GameState> retrieveMyGames()
	{
		return null;
	}

	public static List<GameState> retrieveOpenGames()
	{
		return null;
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

	public static void main(String args[])
	{
		// TODO remove
		List<String> games = ServerInterface.retrieveDeployedGames();

		for (String game : games)
		{
			System.out.println(game);
		}
	}
}
