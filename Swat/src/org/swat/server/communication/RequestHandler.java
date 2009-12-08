package org.swat.server.communication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.List;

import org.swat.data.Coordinate;
import org.swat.data.DataParsing;
import org.swat.data.GameInfo;
import org.swat.data.GameState;
import org.swat.data.LineReader;
import org.swat.server.controller.ServerController;

public class RequestHandler implements Runnable
{
	private final Socket socket;
	private final ServerController controller;
	private LineReader reader;
	private PrintWriter writer;
	private StringBuffer writeBuffer;
	private boolean error = false;
	private String username;

	public RequestHandler(Socket socket)
	{
		this.socket = socket;
		controller = ServerController.getInstance();
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
		username = reader.getLine().substring(9);

		if (!reader.advance().startsWith("password:"))
		{
			sendError("Malformed password line");
			return;
		}
		String password = reader.getLine().substring(9);

		// Authenticate
		if (!controller.userAuthenticate(username, password))
		{
			// Create a new user since registration is not implemented
			controller.addUser(username, password);
			// sendError("Invalid login credentials");
			// return;
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
		if (!error)
		{
			writer.println("END_RESPONSE");
		}
	}

	private void sendError(String message)
	{
		writeBuffer.setLength(0);
		writer.println("ERROR: " + message);
		error = true;
	}

	private void createGame()
	{
		String gameName;

		// Read the gameName
		if ((gameName = reader.advance()) == null)
		{
			sendError("Incomplete request");
			return;
		}

		// Create the game
		GameState state = controller.createGame(gameName, username);
		if (state == null)
		{
			sendError("Unable to create game");
			return;
		}

		// Write the results
		DataParsing.writeGameState(writer, state);
	}

	private void joinGame()
	{
		int gameID;

		// Read the gameID
		try
		{
			gameID = Integer.parseInt(reader.advance());
		}
		catch (NumberFormatException e)
		{
			sendError("Invalid gameID received");
			return;
		}

		// Add the user to the game
		GameState state = controller.joinGame(gameID, username);
		if (state == null)
		{
			sendError("Unable to join game");
			return;
		}

		// Write the results
		DataParsing.writeGameState(writer, state);
	}

	private void makeMove()
	{
		int gameID, stateID;

		// Read the gameID
		try
		{
			gameID = Integer.parseInt(reader.advance());
		}
		catch (NumberFormatException e)
		{
			sendError("Invalid gameID received");
			return;
		}

		// Read the stateID
		try
		{
			stateID = Integer.parseInt(reader.advance());
		}
		catch (NumberFormatException e)
		{
			sendError("Invalid stateID received");
			return;
		}

		// Read the list of coordinates
		List<Coordinate> coords = DataParsing.readCoordinateList(reader);
		if (coords == null)
		{
			sendError("Incomplete request");
			return;
		}

		// Update the game
		GameState state = controller
				.makeMove(gameID, stateID, username, coords);

		// Write the results
		DataParsing.writeGameState(writer, state);
	}

	private void retrieveDeployedGames()
	{
		// Get a list of the deployed games
		List<String> games = controller.retrieveDeployedGames();

		// Write the results
		DataParsing.writeStringList(writer, games);
	}

	private void retrieveGameInfo()
	{
		String gameName;

		// Read the gameName
		if ((gameName = reader.advance()) == null)
		{
			sendError("Incomplete request");
			return;
		}

		// Retrieve the GameInfo
		GameInfo info = controller.getGameInfo(gameName);
		if (info == null)
		{
			sendError("Could not find game '" + gameName + "'");
			return;
		}

		// Write the results
		DataParsing.writeGameInfo(writer, info);
	}

	private void retrieveGameState()
	{
		int gameID;

		// Read the gameID
		try
		{
			gameID = Integer.parseInt(reader.advance());
		}
		catch (NumberFormatException e)
		{
			sendError("Invalid gameID received");
			return;
		}

		// Retrieve the GameState
		GameState state = controller.retrieveGameState(gameID);
		if (state == null)
		{
			sendError("Could not find game instance #" + gameID);
			return;
		}

		// Write the results
		DataParsing.writeGameState(writer, state);
	}

	private void retrieveMyGames()
	{
		// Retrieve a list of the user's active games
		List<GameState> games = controller.retrieveMyGame(username);

		// Write the results
		DataParsing.writeGameStateList(writer, games);
	}

	private void retrieveOpenGames()
	{
		// Retrieve a list of open games
		List<GameState> games = controller.retrieveOpenGames();

		// Write the results
		DataParsing.writeGameStateList(writer, games);
	}
}
