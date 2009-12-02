package org.swat.server.communication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.swat.data.DataParsing;
import org.swat.data.LineReader;

public class RequestHandler implements Runnable
{
	private final Socket socket;
	private LineReader reader;
	private PrintWriter writer;
	private StringBuffer writeBuffer;

	public RequestHandler(Socket socket)
	{
		this.socket = socket;
	}

	@Override
	public void run()
	{
		try
		{
			/*
			 * Instead of writing directly to the socket's output stream I use
			 * an intermediate StringBuffer. This allows us to clear the buffer
			 * in case of an error instead of sending an incomplete response.
			 */
			StringWriter stringWriter = new StringWriter(256);
			writeBuffer = stringWriter.getBuffer();
			writer = new PrintWriter(stringWriter, true);
			reader = new LineReader(new InputStreamReader(socket
					.getInputStream()));

			// Process the request
			processRequest();

			// Copy the buffer to the output stream
			OutputStreamWriter out = new OutputStreamWriter(socket
					.getOutputStream());
			out.write(writeBuffer.toString());
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			System.err.println("Error processing client request");
			e.printStackTrace();
			return;
		}
		finally
		{
			try
			{
				// Close everything
				writer.close();
				reader.close();
				socket.close();
			}
			catch (IOException e)
			{
				System.err.println("Error disconnecting from client");
				e.printStackTrace();
			}
		}
	}

	private void processRequest()
	{
		// Read the username and password
		if (!reader.advance().startsWith("username:"))
		{
			sendError("Malformed username line");
			return;
		}
		String username = reader.getLine().substring(9);

		if (!reader.advance().startsWith("password:"))
		{
			sendError("Malformed password line");
			return;
		}
		String password = reader.getLine().substring(9);

		// TODO Authenticate
		if (!username.equals("username") || !password.equals("password"))
		{
			sendError("Invalid login credentials");
			return;
		}

		// Start the response
		writer.println("BEGIN_RESPONSE");

		// Branch depending on the command
		if (reader.advance().equals("createGame"))
		{
			createGame();
		}
		else if (reader.getLine().equals("joinGame"))
		{
			joinGame();
		}
		else if (reader.getLine().equals("makeMove"))
		{
			makeMove();
		}
		else if (reader.getLine().equals("retrieveDeployedGames"))
		{
			retrieveDeployedGames();
		}
		else if (reader.getLine().equals("retrieveGameInfo"))
		{
			retrieveGameInfo();
		}
		else if (reader.getLine().equals("retrieveGameState"))
		{
			retrieveGameState();
		}
		else if (reader.getLine().equals("retrieveMyGames"))
		{
			retrieveMyGames();
		}
		else if (reader.getLine().equals("retrieveOpenGames"))
		{
			retrieveOpenGames();
		}
		else
		{
			sendError("Unsupported command");
			return;
		}

		// Read the end of the command
		if (!reader.advance().equals("END_REQUEST"))
		{
			sendError("Malformed request");
			return;
		}

		// End the response
		writer.println("END_RESPONSE");
	}

	private void sendError(String message)
	{
		writeBuffer.setLength(0);
		writer.println("ERROR: " + message);
	}

	private void createGame()
	{
	}

	private void joinGame()
	{
	}

	private void makeMove()
	{
	}

	private void retrieveDeployedGames()
	{
		// TODO Get the list of games
		List<String> games = new ArrayList<String>();
		games.add("Chess");
		games.add("Checkers");
		games.add("Tic-Tac-Toe");

		// Write the list of games
		DataParsing.writeStringList(writer, games);
	}

	private void retrieveGameInfo()
	{
	}

	private void retrieveGameState()
	{
	}

	private void retrieveMyGames()
	{
	}

	private void retrieveOpenGames()
	{
	}
}
