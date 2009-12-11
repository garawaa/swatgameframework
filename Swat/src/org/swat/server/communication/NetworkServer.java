package org.swat.server.communication;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Listens for clients on a TCP socket and spawns RequestHandlers for them
 * 
 * @author tombuzbee
 * 
 */
public class NetworkServer implements Runnable
{
	private final int port;

	/**
	 * Creates a server with default port 9876
	 */
	public NetworkServer()
	{
		this(9876);
	}

	/**
	 * Creates a server with the specified port
	 * 
	 * @param port
	 *            Port to listen on
	 */
	public NetworkServer(int port)
	{
		this.port = port;
	}
	
	public void run()
	{
		ServerSocket serverSocket = null;
		boolean listening = true;

		// Open the socket to listen for connections
		try
		{
			serverSocket = new ServerSocket(port);
		}
		catch (IOException e)
		{
			System.err.println("Could not listen on port: " + port);
			System.exit(-1);
		}

		// Wait for connections and spawn RequestHandler threads
		while (listening)
		{
			try
			{
				new Thread(new RequestHandler(serverSocket.accept())).start();
			}
			catch (IOException e)
			{
				System.err.println("Accept failed: " + port);
			}
		}

		// Close the socket (won't ever get here under normal execution)
		try
		{
			serverSocket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Main method for the SWAT server.
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		new Thread(new NetworkServer()).start();
	}
}
