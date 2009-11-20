package org.swat.server.communication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.swat.data.DataParsing;
import org.swat.data.LineReader;

public class RequestHandler implements Runnable
{
	private final Socket socket;

	public RequestHandler(Socket socket)
	{
		this.socket = socket;
	}

	@Override
	public void run()
	{
		LineReader in = null;
		PrintWriter out = null;

		try
		{
			// Create the reader and writer
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new LineReader(new InputStreamReader(socket.getInputStream()));

			// Process the request
			processRequest(in, out);
		}
		catch (IOException e)
		{
			System.err.println("Error connecting to client");
			e.printStackTrace();
			return;
		}
		finally
		{
			try
			{
				// Close everything
				out.close();
				in.close();
				socket.close();
			}
			catch (IOException e)
			{
				System.err.println("Error disconnecting from client");
				e.printStackTrace();
			}
		}
	}

	private void processRequest(LineReader reader, PrintWriter writer)
	{
		// Read the username and password
		DataParsing.verify(writer, reader.advance().startsWith("username:"),
				"Malformed username line");
		String username = reader.getLine().substring(9);
		DataParsing.verify(writer, reader.advance().startsWith("password:"),
				"Malformed password line");
		String password = reader.getLine().substring(9);

		// Authenticate
		if (!username.equals("username") || !password.equals("password"))
		{
			// TODO
		}

		// Start the response
		writer.println("BEGIN_RESPONSE");

		// Branch depending on the command
		if (reader.advance().equals("createGame"))
		{
			createGame(reader, writer);
		}
		else if (reader.getLine().equals("joinGame"))
		{
			joinGame(reader, writer);
		}
		else if (reader.getLine().equals("makeMove"))
		{
			makeMove(reader, writer);
		}
		else if (reader.getLine().equals("retrieveDeployedGames"))
		{
			retrieveDeployedGames(reader, writer);
		}
		else if (reader.getLine().equals("retrieveGameInfo"))
		{
			retrieveGameInfo(reader, writer);
		}
		else if (reader.getLine().equals("retrieveGameState"))
		{
			retrieveGameState(reader, writer);
		}
		else if (reader.getLine().equals("retrieveMyGames"))
		{
			retrieveMyGames(reader, writer);
		}
		else if (reader.getLine().equals("retrieveOpenGames"))
		{
			retrieveOpenGames(reader, writer);
		}
		else
		{
			DataParsing.verify(writer, false, "Unsupported command");
		}

		// Read the end of the command
		DataParsing.verify(writer, reader.advance().equals("END_REQUEST"),
				"Malformed request");

		// End the response
		writer.println("END_RESPONSE");
	}

	public void createGame(LineReader reader, PrintWriter writer)
	{
	}

	public void joinGame(LineReader reader, PrintWriter writer)
	{
	}

	public void makeMove(LineReader reader, PrintWriter writer)
	{
	}

	public void retrieveDeployedGames(LineReader reader, PrintWriter writer)
	{
		// Get the list of games
		// TODO
		List<String> games = new ArrayList<String>();
		games.add("Chess");
		games.add("Checkers");
		games.add("Tic-Tac-Toe");

		// Write the list of games
		DataParsing.writeStringList(writer, games);
	}

	public void retrieveGameInfo(LineReader reader, PrintWriter writer)
	{
	}

	public void retrieveGameState(LineReader reader, PrintWriter writer)
	{
	}

	public void retrieveMyGames(LineReader reader, PrintWriter writer)
	{
	}

	public void retrieveOpenGames(LineReader reader, PrintWriter writer)
	{
	}
}
