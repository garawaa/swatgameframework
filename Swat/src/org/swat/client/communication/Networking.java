package org.swat.client.communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.swat.data.LineReader;

public class Networking
{
	public static final String SERVER_IP = "127.0.0.1";
	public static final int SERVER_PORT = 9876;

	private static Socket socket = null;
	private static PrintWriter writer = null;
	private static LineReader reader = null;

	public static PrintWriter getWriter()
	{
		// Return existing writer
		if (writer != null)
		{
			return writer;
		}

		// Check for an open socket
		if (socket == null)
		{
			openSocket();
		}

		// Connect a writer to the output stream
		try
		{
			writer = new PrintWriter(socket.getOutputStream(), true);
			return writer;
		}
		catch (IOException e)
		{
			System.err.println("Error writing to stream");
			e.printStackTrace();
			return null;
		}
	}

	public static LineReader getReader()
	{
		// Return existing reader
		if (reader != null)
		{
			return reader;
		}

		// Check for an open socket
		if (socket == null)
		{
			openSocket();
		}

		// Connect a reader to the input stream
		try
		{
			reader = new LineReader(socket.getInputStream());
			return reader;
		}
		catch (IOException e)
		{
			System.err.println("Error reading from stream");
			e.printStackTrace();
			return null;
		}
	}

	private static void openSocket()
	{
		try
		{
			socket = new Socket(SERVER_IP, SERVER_PORT);
		}
		catch (UnknownHostException e)
		{
			System.err.println("Host not found: " + SERVER_IP);
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.err.println("Unable to connect to " + SERVER_IP + ":"
					+ SERVER_PORT);
			e.printStackTrace();
		}
	}

	public static void closeConnection()
	{
		// Close socket and streams
		try
		{
			writer.close();
			writer = null;
			reader.close();
			reader = null;
			socket.close();
			socket = null;
		}
		catch (IOException e)
		{
			System.err.println("Error disconnecting from server");
			e.printStackTrace();
		}
	}
}
