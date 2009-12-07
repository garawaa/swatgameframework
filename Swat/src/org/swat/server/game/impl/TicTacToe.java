package org.swat.server.game.impl;

import java.util.HashMap;

import org.swat.data.Coordinate;
import org.swat.data.GAME_STATE;
import org.swat.data.GAME_TYPE;
import org.swat.data.GameInfo;
import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.server.game.Game;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;

public class TicTacToe implements Game {

	private static final int ID = 1;
	private static final String name = "TicTacToe";
	private static final int numberOfPlayersNeeded = 2;
	private static final int boardLength = 3;
	private static final int boardWidth = 3;

	private static GameInfo gameInfo;
	
	private static TicTacToe instance;

	private static HashMap<Integer, String> pieces = null;
	private static GameState initialState = null;
	
	@Override
	public GameInfo getGameInfo() {
		return gameInfo;
	}

	private TicTacToe() {

		pieces = new HashMap<Integer, String>();
		pieces.put(1, "CHECK");
		pieces.put(2, "CIRCLE");

		initialState = new GameState();
		initialState.setPieceInfo(new int[3][3]);
		
		//init gameinfo
		gameInfo = new GameInfo();
		gameInfo.setBoardLength(boardLength);
		gameInfo.setBoardWidth(boardWidth);
		gameInfo.setGameID(ID);
		gameInfo.setGameName(name);
		gameInfo.setGameType(GAME_TYPE.ADD);
		gameInfo.setNumPlayersNeeded(numberOfPlayersNeeded);
		gameInfo.setPieces(pieces);
		
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

		/*
		 * if it is not the turn of this player, error
		 * 
		 * increment counter
		 * if move is invalid, error
		 * check for winner, set accordingly
		 * check for draw, set accordingly
		 * update with results of move, return state
		 */
		synchronized (state) {

			if (state.getGameState() != GAME_STATE.STARTED)
				throw new IllegalGameStateException();

			if (!state.getTurnOfPlayer().equals(move.getPlayerUID()))
				throw new IllegalMoveException();
			
			Coordinate[] moveCoordinates = new Coordinate[1];
			moveCoordinates = move.getMoveCoordinates().toArray(moveCoordinates);
			
			int x = moveCoordinates[0].getX();
			int y = moveCoordinates[0].getY();
			
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
			state.incrementCounter();			

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

}
