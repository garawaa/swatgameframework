package org.swat.desktopclient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.swat.data.Coordinate;
import org.swat.data.GameState;
import org.swat.data.IGameInfo;
import org.swat.desktopclient.communication.ServerInterface;

/**
 * Main controller for the client
 * 
 * @author tombuzbee
 * 
 */
public class Controller
{
	private String gameName = "Tic-Tac-Toe";
	private int gameInstanceID = GameState.UNDEFINED_INSTANCE_ID;
	private IGameInfo info = ServerInterface.retrieveGameInfo(gameName);
	private GameState state = null;
	private final Collection<StateListener> stateListeners = new ArrayList<StateListener>();

	private static Controller instance = null;

	/**
	 * Singleton access
	 * 
	 * @return
	 */
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
						Thread.sleep(1000);
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

	/**
	 * @return
	 */
	public GameState getState()
	{
		return state;
	}

	/**
	 * @return
	 */
	public String getGameName()
	{
		return gameName;
	}

	/**
	 * @param gameName
	 */
	public void setGameName(String gameName)
	{
		this.gameName = gameName;
		info = ServerInterface.retrieveGameInfo(gameName);
		state = null;
	}

	/**
	 * @return
	 */
	public IGameInfo getInfo()
	{
		return info;
	}

	/**
	 * Used to report user actions, i.e. mouse clicks
	 * 
	 * @param x
	 *            The x coordinate of the action
	 * @param y
	 *            The y coordinate of the action
	 */
	public void boardAction(int x, int y)
	{
		List<Coordinate> list = new ArrayList<Coordinate>();
		list.add(new Coordinate(x, y));
		ServerInterface.makeMove(gameInstanceID, state.getCounter(), list);
		updateState();
	}

	/**
	 * Lets the controller know to update the listener when the game state
	 * changes
	 * 
	 * @param listener
	 */
	public void registerStateListener(StateListener listener)
	{
		stateListeners.add(listener);
	}

	private synchronized void updateState()
	{
		if (gameInstanceID == GameState.UNDEFINED_INSTANCE_ID)
		{
			state = info.getInitialState();
		}
		else
		{
			state = ServerInterface.retrieveGameState(gameInstanceID);
		}

		// Notify listeners
		for (StateListener listener : stateListeners)
		{
			listener.updateState();
		}
	}

	/**
	 * Creates a game
	 */
	public void createGame()
	{
		ServerInterface.setUsername("User" + System.currentTimeMillis());
		state = ServerInterface.createGame(gameName);
		gameInstanceID = state.getGameInstanceID();
	}

	/**
	 * Adds user to an existing game
	 * 
	 * @param gameID
	 *            The gameInstanceID of the game to join
	 */
	public void joinGame(int gameID)
	{
		ServerInterface.setUsername("User" + System.currentTimeMillis());
		state = ServerInterface.joinGame(gameID);
		this.gameInstanceID = state.getGameInstanceID();
	}

	/**
	 * @return
	 */
	public String getUsername()
	{
		return ServerInterface.getUsername();
	}

	/**
	 * @return The gameInstanceID of the current game
	 */
	public int getGameID()
	{
		return gameInstanceID;
	}
}
