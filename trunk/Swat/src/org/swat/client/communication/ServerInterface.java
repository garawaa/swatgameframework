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
	private LineReader reader;
	private PrintWriter writer;

	private void connect()
	{
		reader = Networking.getReader();
		writer = Networking.getWriter();
	}

	private void disconnect()
	{
		Networking.closeConnection();
		reader = null;
		writer = null;
	}

	private void writeAuthenticationInfo()
	{
		// TODO
		writer.println("username:username");
		writer.println("password:password");
	}

	private void verifyResponseOpening()
	{
		DataParsing.verify(new PrintWriter(System.err), reader.advance()
				.equals("BEGIN_RESPONSE"), "Malformed response");
	}

	private void verifyResponseClosing()
	{
		DataParsing.verify(new PrintWriter(System.err), reader.advance()
				.equals("END_RESPONSE"), "Malformed response");
	}

	GameState createGame(String gameType)
	{
		return null;
	}

	GameState joinGame(int gameID)
	{
		return null;
	}

	GameState makeMove(List<Coordinate> coordinates)
	{
		return null;
	}

	List<String> retrieveDeployedGames()
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

	GameInfo retrieveGameInfo(String gameType)
	{
		return null;
	}

	GameState retrieveGameState(int gameID)
	{
		return null;
	}

	List<GameState> retrieveMyGames()
	{
		return null;
	}

	List<GameState> retrieveOpenGames()
	{
		return null;
	}

	public static void main(String args[])
	{
		ServerInterface server = new ServerInterface();
		List<String> games = server.retrieveDeployedGames();

		for (String game : games)
		{
			System.out.println(game);
		}
	}
}
