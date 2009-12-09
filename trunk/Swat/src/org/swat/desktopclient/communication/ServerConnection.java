package org.swat.desktopclient.communication;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.swat.data.LineReader;

public class ServerConnection
{
	private String serverIP;
	private int serverPort;
	private Socket socket = null;
	private PrintWriter writer = null;
	private LineReader reader = null;

	public ServerConnection(String serverIP, int serverPort)
	{
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

	public PrintWriter getWriter()
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

	public LineReader getReader()
	{
		// Return existing reader
		if (reader != null)
		{
			return reader;
		}

		 //Check for an open socket
		if (socket == null)
		{
			openSocket();
		}
	     // Connect a reader to the input stream
        try
        {
			reader = new LineReader(new InputStreamReader(socket
					.getInputStream()));
                return reader;
        }
        catch (IOException e)
        {
                System.err.println("Error reading from stream");
                e.printStackTrace();
                return null;
        }

	}

	private void openSocket()
	{
		try
		{
			socket = new Socket(serverIP, serverPort);
		}
		catch (UnknownHostException e)
		{
			System.err.println("Host not found: " + serverIP);
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.err.println("Unable to connect to " + serverIP + ":"
					+ serverPort);
			e.printStackTrace();
		}
	}

	public void closeConnection()
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

	public String getServerIP()
	{
		return serverIP;
	}

	public void setServerIP(String serverIP)
	{
		this.serverIP = serverIP;
	}

	public int getServerPort()
	{
		return serverPort;
	}

	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}
}
