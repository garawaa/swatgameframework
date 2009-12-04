package org.swat.server.communication;

import java.io.IOException;
import java.net.ServerSocket;

public class NetworkServer implements Runnable
{
	private final int port;

	public NetworkServer()
	{
		this(9876);
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String args[])
	{
		// TODO remove
		new Thread(new NetworkServer()).start();
	}
}
