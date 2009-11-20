package org.swat.server.gameimpl;

import java.util.HashMap;

import org.swat.data.GAME_STATE;
import org.swat.data.GAME_TYPE;
import org.swat.data.GameInfo;
import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.data.MoveCoordinate;
import org.swat.server.game.Game;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;

public class TicTacToe implements Game {

	private static final int ID = 1;
	private static final String name = "TicTacToe";
	private static final int numberOfPlayersNeeded = 2;
	private static final int boardLength = 3;
	private static final int boardWidth = 3;

	private GameInfo gameInfo;
	
	private static TicTacToe instance;

	private static HashMap<Integer, String> pieces = null;
	private static GameState initialState = null;

	private TicTacToe() {

		pieces = new HashMap<Integer, String>();
		pieces.put(1, "Check");
		pieces.put(2, "Circle");

		initialState = new GameState(this);
		initialState.setPieceInfo(new int[3][3]);

	}

	public static synchronized Game getLogic() {

		if (instance == null)
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
	public GameState makeMove(GameState state, GameMove move)
			throws IllegalGameStateException, IllegalMoveException {

		synchronized (state) {

			if (state.getGameState() != GAME_STATE.STARTED)
				throw new IllegalGameStateException();

			if (!state.getTurnOfPlayer().equals(move.getPlayerUID()))
				throw new IllegalMoveException();
			
			MoveCoordinate[] moveCoordinates = new MoveCoordinate[1];
			moveCoordinates = move.getMoveCoordinates().toArray(moveCoordinates);
			
			int x = moveCoordinates[0].getFrom().getX();
			int y = moveCoordinates[0].getFrom().getY();
			
			if(state.getPieceInfo()[x][y] != 0)
				throw new IllegalMoveException();
			
			int playerNumber = state.getPlayerNumber(move.getPlayerUID());
			state.getPieceInfo()[x][y] = playerNumber;
			
			state.setGameState(GAME_STATE.DRAWN);
			int[][] pieces = state.getPieceInfo();
			
			//check for draw
			for(int loop1=0; loop1<3; loop1++)
				for(int loop2=0; loop2<3; loop2++)
					if(pieces[loop1][loop2] == 0)
						state.setGameState(GAME_STATE.STARTED);
			
			//check for winner
			int pieceInfo[][] = state.getPieceInfo();
			int sum[] = new int[8];
			
			for(int loop1=0; loop1<3; loop1++)
				for(int loop2=0; loop2<3; loop2++) {
					sum[loop1] += pieceInfo[loop1][loop2];
					sum[loop1+3] += pieceInfo[loop2][loop1];
					if(pieceInfo[loop1][loop2] == 0) {
						sum[loop1] = -6;
						sum[loop2+3] = -6;
					}
						
				}
			
			for(int loop1=0; loop1<3; loop1++) {
				sum[6] += pieceInfo[loop1][loop1];
				sum[7] += pieceInfo[2-loop1][2-loop1];
			}
			
			for(int loop1=0; loop1<8; loop1++) {
			
				if(sum[loop1] == 3 || sum[loop1] == 6) {
					state.setWinnerID(move.getPlayerUID());
					state.setGameState(GAME_STATE.FINISHED);
					break;
				}
					
			}
			
			//increment state ID
			state.incrementStateID();			

		}
		
		return state;
		
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

	@Override
	public GAME_TYPE getGameType() {
		return GAME_TYPE.ADD;
	}

	public void setGameInfo(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}

	public GameInfo getGameInfo() {
		return gameInfo;
	}

}
