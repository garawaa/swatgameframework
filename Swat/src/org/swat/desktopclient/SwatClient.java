package org.swat.desktopclient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class SwatClient extends JFrame
{
	private static final long serialVersionUID = -8599957408414899969L;

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
			String gameID = JOptionPane.showInputDialog(this,
					"Enter the Game ID to join");
			Controller.getInstance().joinGame(Integer.parseInt(gameID));
		}

		// Update the title with the gameID
		int gameID = Controller.getInstance().getGameID();
		this.setTitle("SWAT Game (" + gameID + ")");

		// Add the billboard
		this.getContentPane().add(new BoardPanel());

		this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
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
