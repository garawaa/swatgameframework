package org.swat.desktopclient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.swat.desktopclient.communication.ServerInterface;

/**
 * Simple SWAT client. Mostly generic, but only supports Tic-Tac-Toe right now
 * 
 * @author tombuzbee
 * 
 */
public class SwatClient extends JFrame
{
	private static final long serialVersionUID = -8599957408414899969L;

	/**
	 * Prompts the user for info on joining a game, then creates the main frame
	 */
	public SwatClient()
	{
		// Initialize the window
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(320, 340);
		this.setLocation(200, 200);

		// Ask the user what kind of session to begin
		Object[] options = {"Create", "Join"};
		int choice = JOptionPane.showOptionDialog(this,
				"Create a new game or join an existing game?",
				"Create or join?",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		
		// Tell the controller the user's choice
		if (choice == 0)
		{
			Controller.getInstance().createGame();
		}
		else
		{
			String gameInstanceID = JOptionPane.showInputDialog(this,
					"Enter the ID of the game you wish to join:");
			if (gameInstanceID == null)
				System.exit(0);
			Controller.getInstance().joinGame(Integer.parseInt(gameInstanceID));
		}

		// Update the title with the gameID
		int gameID = Controller.getInstance().getGameID();
		this.setTitle("SWAT Game (" + gameID + ")");

		// Add the billboard
		this.getContentPane().add(new BoardPanel());

		this.setVisible(true);
	}
	
	/**
	 * Reads the IP address from the command line, then starts the GUI
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		// Get the IP address from the command line
		if (args.length == 1)
		{
			if (args[0].matches("[0-9][0-9]?[0-9]?\\.[0-9][0-9]?[0-9]?\\."
					+ "[0-9][0-9]?[0-9]?\\.[0-9][0-9]?[0-9]?"))
			{
				ServerInterface.setServerIP(args[0]);
			}
		}
		System.out.println("Using server IP " + ServerInterface.getServerIP());

		// Set the widget style
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
		}

		// Create the frame
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				new SwatClient().setVisible(true);
			}
		});
	}

}
