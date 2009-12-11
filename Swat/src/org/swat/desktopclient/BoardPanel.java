package org.swat.desktopclient;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.swat.data.GAME_STATE;
import org.swat.data.GameState;
import org.swat.data.IGameInfo;

public class BoardPanel extends JPanel implements StateListener
{
	private static final long serialVersionUID = -1197773687175269925L;

	private BufferedImage backgroundImage;
	private final Map<Integer, BufferedImage> pieceImages = new HashMap<Integer, BufferedImage>();
	private final IGameInfo info;
	private GameState state;
	private final Controller controller = Controller.getInstance();

	public BoardPanel()
	{
		this.info = controller.getInfo();
		loadGraphics();
		
		this.addMouseListener(new BoardMouseListener());
		controller.registerStateListener(this);
	}

	private void loadGraphics()
	{
		String dir = "res" + File.separatorChar + info.getGameName()
				+ File.separatorChar;
		String filename = dir + "board.png";

		try
		{
			backgroundImage = ImageIO.read(new File(filename));

			Map<Integer, String> pieces = info.getPieces();
			for (int i : pieces.keySet())
			{
				filename = dir + pieces.get(i) + ".png";
				BufferedImage image = ImageIO.read(new File(filename));
				pieceImages.put(i, image);
			}
		}
		catch (IOException e)
		{
			System.err.println("Could not open file: " + filename);
			e.printStackTrace();
		}
	}

	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;

		// Draw the background
		g2d.drawImage(backgroundImage, null, 0, 0);

		if (state == null)
			return;

		// Draw the pieces
		int[][] pieces = state.getPieceInfo();
		int squareWidth = backgroundImage.getWidth() / info.getBoardWidth();
		int squareHeight = backgroundImage.getHeight() / info.getBoardLength();

		for (int y = 0; y < info.getBoardLength(); y++)
		{
			for (int x = 0; x < info.getBoardWidth(); x++)
			{
				BufferedImage piece = pieceImages.get(pieces[x][y]);
				g2d.drawImage(piece, null, x * squareWidth, y * squareHeight);
			}
		}
	}

	@Override
	public void updateState()
	{
		state = controller.getState();
		repaint();
		
		// Display end-of-game messages
		if (state.getGameState() == GAME_STATE.FINISHED)
		{
			if (state.getWinnerID().equals(controller.getUsername()))
			{
				JOptionPane.showMessageDialog(this.getParent(), "You win!",
						"You win!", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
			else
			{
				JOptionPane.showMessageDialog(this.getParent(), "You lose",
						"You lose", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}
		else if (state.getGameState() == GAME_STATE.DRAWN)
		{
			JOptionPane.showMessageDialog(this.getParent(), "There was a tie",
					"There was a tie", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}

	private class BoardMouseListener implements MouseListener
	{

		@Override
		public void mouseClicked(MouseEvent e)
		{
			int x = (e.getX() * info.getBoardWidth())
					/ backgroundImage.getWidth();
			int y = (e.getY() * info.getBoardLength())
					/ backgroundImage.getHeight();
			controller.boardAction(x, y);
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
		}
	}
}
