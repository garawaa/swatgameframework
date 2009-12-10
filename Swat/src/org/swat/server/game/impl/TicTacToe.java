package org.swat.server.game.impl;

import java.util.HashMap;

import org.swat.data.Coordinate;
import org.swat.data.GAME_STATE;
import org.swat.data.GAME_TYPE;
import org.swat.data.GameInfo;
import org.swat.data.GameMove;
import org.swat.data.GameState;
import org.swat.server.game.IGame;
import org.swat.server.game.exceptions.IllegalGameStateException;
import org.swat.server.game.exceptions.IllegalMoveException;

public class TicTacToe implements IGame {

	private static final int ID = 1;
	private static final String name = "Tic-Tac-Toe";
	private static final int numberOfPlayersNeeded = 2;
	private static final int boardLength = 3;
	private static final int boardWidth = 3;

	private static GameInfo gameInfo;
	
	private static TicTacToe instance;

	private static HashMap<Integer, String> pieces = null;
	private static GameState initialState = null;

	private TicTacToe() {

		pieces = new HashMap<Integer, String>();
		pieces.put(1, "x");
		pieces.put(2, "o");

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
		gameInfo.setInitialState(initialState);
		
	}

	public static synchronized IGame getLogic() {

		if (instance == null)
			instance = new TicTacToe();

		return instance;

	}
	
	/*
	 * Provides all the information needed for this game
	 * @see org.swat.server.game.IGame#getGameInfo()
	 */
	@Override
	public GameInfo getGameInfo() {
		return gameInfo;
	}
	

	/*
	 * Game logic
	 */

	@Override
	public GameState makeMove(GameState state, GameMove move)
			throws IllegalGameStateException, IllegalMoveException {

		/*
		 * if it is not the turn of this player, error
		 * 
		 * check for winner, set accordingly
		 * check for draw, set accordingly
		 * update with results of move, return state
		 */
		synchronized (this) {

			if (state.getGameState() != GAME_STATE.STARTED)
				throw new IllegalGameStateException();

			if (!state.getTurnOfPlayer().equals(move.getPlayerUID()))
				throw new IllegalMoveException();
			
			if (state.getCounter() != move.getCounter())
				throw new IllegalMoveException();

			Coordinate[] moveCoordinates = new Coordinate[1];
			moveCoordinates = move.getMoveCoordinates().toArray(moveCoordinates);
			
			int x = moveCoordinates[0].getX();
			int y = moveCoordinates[0].getY();
			
			if(state.getPieceInfo()[x][y] != 0)
				throw new IllegalMoveException();
			
			int playerNumber = state.getPlayerNumber(move.getPlayerUID()) + 1;
			state.getPieceInfo()[x][y] = playerNumber;
			
			//set the turn of player
			state.updatePlayerTurns();
			state.incrementCounter();
			
			state.setGameState(GAME_STATE.DRAWN);
			int[][] pieces = state.getPieceInfo();
			

			for (int i = 0; i < state.getPieceInfo()[0].length; i++)
			{
				for (int j = 0; j < state.getPieceInfo().length; j++)
				{
					System.out
							.print(state.getPieceInfo()[j][i] + "   ");
				}
				System.out.println();
			}
			System.out.println();
			
			// Check for a winner
			if ((pieces[0][0] == pieces[1][0] && pieces[1][0] == pieces[2][0] && pieces[2][0] != 0)
					|| (pieces[0][1] == pieces[1][1]
							&& pieces[1][1] == pieces[2][1] && pieces[2][1] != 0)
					|| (pieces[0][2] == pieces[1][2]
							&& pieces[1][2] == pieces[2][2] && pieces[2][2] != 0)
					|| (pieces[0][0] == pieces[0][1]
							&& pieces[0][1] == pieces[0][2] && pieces[0][2] != 0)
					|| (pieces[1][0] == pieces[1][1]
							&& pieces[1][1] == pieces[1][2] && pieces[1][2] != 0)
					|| (pieces[2][0] == pieces[2][1]
							&& pieces[2][1] == pieces[2][2] && pieces[2][2] != 0)
					|| (pieces[0][0] == pieces[1][1]
							&& pieces[1][1] == pieces[2][2] && pieces[2][2] != 0)
					|| (pieces[2][0] == pieces[1][1]
							&& pieces[1][1] == pieces[0][2] && pieces[0][2] != 0))
			{
				state.setWinnerID(move.getPlayerUID());
				state.setGameState(GAME_STATE.FINISHED);

				System.out.println("Finished");
				
				return (state);
			}

			// See if there are still empty spaces
			for(int loop1=0; loop1<3; loop1++)
				for(int loop2=0; loop2<3; loop2++)
					if(pieces[loop1][loop2] == 0) {
						state.setGameState(GAME_STATE.STARTED);
						System.out.println("Started");
						return (state);
					}
		}
			System.out.println("Drawn");
		return state;
		
	}

	/*
	 * (end) game logic
	 */	

}
