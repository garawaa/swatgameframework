package org.swat.client.communication;

import java.io.PrintWriter;
import java.util.List;

import org.swat.client.data.*;

public class ServerInterface
{
	private static LineReader reader;
	private static PrintWriter writer;

	private static void connect()
	{
		//reader = Networking.getReader();
		writer = Networking.getWriter();
	}

	private static void disconnect()
	{
		Networking.closeConnection();
		//reader = null;
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
		DataParsing.verify(new PrintWriter(System.err), reader.advance()
				.equals("BEGIN_RESPONSE"), "Malformed response");
	}

	private static void verifyResponseClosing()
	{
		DataParsing.verify(new PrintWriter(System.err), reader.advance()
				.equals("END_RESPONSE"), "Malformed response");
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

	public static void main(String args[])
	{
		List<String> games = ServerInterface.retrieveDeployedGames();

		for (String game : games)
		{
			System.out.println(game);
		}
	}
}
