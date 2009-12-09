package org.swat.desktopclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.swat.data.Coordinate;
import org.swat.data.GameState;
import org.swat.data.IGameInfo;
import org.swat.desktopclient.communication.ServerInterface;

public class Controller
{
	private String gameName = "Tic-Tac-Toe";
	private int gameID = -1;
	private IGameInfo info = ServerInterface.retrieveGameInfo(gameName);
	private GameState state = null;
	private final Collection<StateListener> stateListeners = new ArrayList<StateListener>();

	private static Controller instance = null;

	public static Controller getInstance()
	{
		if (instance == null)
			instance = new Controller();
		return instance;
	}

	private Controller()
	{
		// Add a thread to poll for new state updates
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (true)
				{
					updateState();
					try
					{
						Thread.sleep(3000);
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	public GameState getState()
	{
		return state;
	}

	public String getGameName()
	{
		return gameName;
	}

	public void setGameName(String gameName)
	{
		this.gameName = gameName;
		info = ServerInterface.retrieveGameInfo(gameName);
		state = null;
	}

	public IGameInfo getInfo()
	{
		return info;
	}

	public void boardAction(int x, int y)
	{
		List<Coordinate> list = new ArrayList<Coordinate>();
		list.add(new Coordinate(x, y));
		ServerInterface.makeMove(gameID, state.getCounter(), list);
		updateState();
	}

	public void registerStateListener(StateListener listener)
	{
		stateListeners.add(listener);
	}

	private synchronized void updateState()
	{
		if (gameID < 0)
		{
			state = info.getInitialState();
		}
		else
		{
			state = ServerInterface.retrieveGameState(gameID);
		}

		// Notify listeners
		for (StateListener listener : stateListeners)
		{
			listener.updateState();
		}
	}

	public void createGame()
	{
		ServerInterface.setUsername("User" + System.currentTimeMillis());
		state = ServerInterface.createGame(gameName);
		gameID = state.getGameInstanceID();
	}

	public void joinGame(int gameID)
	{
		ServerInterface.setUsername("User" + System.currentTimeMillis());
		state = ServerInterface.joinGame(gameID);
		gameID = state.getGameInstanceID();
	}

	public int getGameID()
	{
		return gameID;
	}
}
