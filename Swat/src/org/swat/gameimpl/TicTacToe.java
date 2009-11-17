package org.swat.gameimpl;

import java.util.HashMap;

import org.swat.game.GAME_PLAYERS;
import org.swat.game.Game;
import org.swat.game.GameMove;
import org.swat.game.GameState;

public class TicTacToe implements Game {

	private static final int ID = 1;
	private static final String name = "TicTacToe";
	private static final int numberOfPlayersNeeded = 2;
	private static final int boardLength = 3;
	private static final int boardWidth = 3;
	
	private static TicTacToe instance;
	
	private static HashMap<Integer, String> pieces = null;
	private static GameState initialState = null;
	
	private TicTacToe() {
		
		pieces = new HashMap<Integer, String>();
		pieces.put(0, "Check");
		pieces.put(1, "Circle");
		
		initialState = new GameState();
		initialState.setPieceInfo(new int[3][3]);
		
	}
	
	public static synchronized Game getLogic() {
		
		if(instance == null)
			instance = new TicTacToe();
		
		return instance;
		
	}

	/*
	 * Game logic
	 */
	@Override
	public GameState getInitialState() {
		return initialState;
	}

	@Override
	public HashMap<Integer, String> getPieces() {
		return pieces;
	}
	
	@Override
	public GAME_PLAYERS getPlayerWhoShouldMakeMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean makeMove(GameState state, GameMove move) {
		
		/*
		 * (synchronize on game state)
		 * if not turn, return false
		 * if cell filled, return false
		 * check for winner, set flags accordingly
		 * return true
		 */
		
		
		return false;
	}
	/*
	 * (end) game logic
	 */
	

	/*
	 * Property accessors
	 */
	@Override
	public int getBoardLength() {
		return boardLength;
	}

	@Override
	public int getBoardWidth() {
		return boardWidth;
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getNumberOfPlayersNeeded() {
		return numberOfPlayersNeeded;
	}
	/*
	 * (end) property accessors
	 */

	

}
