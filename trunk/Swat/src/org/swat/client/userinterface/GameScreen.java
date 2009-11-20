package org.swat.client.userinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.swat.client.R;
import org.swat.client.control.Control;
import org.swat.client.data.Coordinate;
import org.swat.client.data.GameInfo;
import org.swat.client.data.GameState;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class GameScreen extends Activity
{
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Menu item constants
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	private static final int submitMove = Menu.FIRST + 1;

	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Private members
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX	
	private GameState gameState;
	private GameInfo gameInfo;

	private ImageView boardImage;

	//TODO use balls instead of imageview for pieces
	private ImageView cross1;
	private ImageView cross2;
	private ImageView cross3;
	private ImageView cross4;
	private ImageView cross5;
	private ImageView cross6;
	private ImageView cross7;
	private ImageView cross8;
	private ImageView cross9;
	private ImageView circle1;
	private ImageView circle2;
	private ImageView circle3;
	private ImageView circle4;
	private ImageView circle5;
	private ImageView circle6;
	private ImageView circle7;
	private ImageView circle8;
	private ImageView circle9;		

	private List<Coordinate> coordinates;

	private float downX = 0;
	private float downY = 0;

	private float gridElementX = 0;
	private float gridElementY = 0;

	private float delta = 0;

	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Menu-related functions
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/** create the menu items */
	private void populateMenu(Menu menu) {

		// enable keyb shortcuts, qwerty mode = true means only show keyb shortcuts (not numeric) and vice versa
		// these only show up in context menu, not options menu
		menu.setQwertyMode(true);

		MenuItem item1 = menu.add(0, submitMove, 0, Strings.submitMove);
		{
			item1.setAlphabeticShortcut('s');
			item1.setIcon(android.R.drawable.btn_star);
		}	
	}

	/** respond to menu item selection */
	private boolean applyMenuChoice(MenuItem item) {		
		switch (item.getItemId()) {
		case submitMove:			
			Control.makeMove(coordinates);
			if(!Control.getCurrentGameState().getIsValid())
			{
				AlertDialog.Builder b = new AlertDialog.Builder(GameScreen.this);
				b.setMessage("Invalid move. Please try again").setPositiveButton(R.string.alert_dialog_ok,
						new OnClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int arg1) {					
					}				
				}).show();
				return false;
			}
			return true;			    
		}
		return false;
	}

	/** hook into menu button for activity */
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		populateMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}

	/** when menu button option selected */
	@Override public boolean onOptionsItemSelected(MenuItem item) {
		return applyMenuChoice(item) || super.onOptionsItemSelected(item);
	}

	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// GUI functions
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

	/** Makes move based on whose  turn it is and where the user has clicked**/
	private void displayPiece(int gameX, int gameY) {	
		boolean playerOnePlaying = false;
		if(Control.getPlayerOneID() == Control.getCurrentGameState().getPlayTurnID())
			playerOnePlaying = true;
		else
			playerOnePlaying = false;

		if(gameX == 0 && gameY == 0)
		{
			if(playerOnePlaying)
			{
				circle1.setVisibility(View.VISIBLE);
			}
			else
			{
				cross1.setVisibility(View.VISIBLE);
			}
		}
		if(gameX == 1 && gameY == 0)
		{
			if(playerOnePlaying)
			{
				circle2.setVisibility(View.VISIBLE);
			}
			else
			{
				cross2.setVisibility(View.VISIBLE);
			}
		}
		if(gameX == 2 && gameY == 0)
		{
			if(playerOnePlaying)
			{
				circle3.setVisibility(View.VISIBLE);
			}
			else
			{
				cross3.setVisibility(View.VISIBLE);
			}
		}
		if(gameX == 0 && gameY == 1)
		{
			if(playerOnePlaying)
			{
				circle4.setVisibility(View.VISIBLE);
			}
			else
			{
				cross4.setVisibility(View.VISIBLE);
			}
		}
		if(gameX == 1 && gameY == 1)
		{
			if(playerOnePlaying)
			{
				circle5.setVisibility(View.VISIBLE);
			}
			else
			{
				cross5.setVisibility(View.VISIBLE);
			}
		}
		if(gameX == 1 && gameY == 2)
		{
			if(playerOnePlaying)
			{
				circle6.setVisibility(View.VISIBLE);
			}
			else
			{
				cross6.setVisibility(View.VISIBLE);
			}
		}
		if(gameX == 0 && gameY == 2)
		{
			if(playerOnePlaying)
			{
				circle7.setVisibility(View.VISIBLE);
			}
			else
			{
				cross7.setVisibility(View.VISIBLE);
			}
		}
		if(gameX == 1 && gameY == 2)
		{
			if(playerOnePlaying)
			{
				circle8.setVisibility(View.VISIBLE);
			}
			else
			{
				cross8.setVisibility(View.VISIBLE);
			}
		}
		else if(gameX == 2 && gameY == 2)
		{
			if(playerOnePlaying)
			{
				circle9.setVisibility(View.VISIBLE);
			}
			else
			{
				cross9.setVisibility(View.VISIBLE);
			}
		}				  
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		

		gameState = Control.getCurrentGameState();
		gameInfo = Control.getCurrentGameInfo();

		String gameType = gameState.getGameType();
		Map<String, Integer> map = new HashMap<String, Integer>();
		coordinates = new ArrayList<Coordinate>();

		if(gameType.contentEquals(Strings.tictactoe))
		{
			setContentView(R.layout.tictactoegamescreen);
			boardImage = (ImageView)this.findViewById(R.id.board);
			boardImage.setImageResource(R.drawable.tictactoeboard);

			cross1 = (ImageView)this.findViewById(R.id.cross1);			
			cross1.setVisibility(View.INVISIBLE);			
			cross2 = (ImageView)this.findViewById(R.id.cross2);			
			cross2.setVisibility(View.INVISIBLE);
			cross3 = (ImageView)this.findViewById(R.id.cross3);			
			cross3.setVisibility(View.INVISIBLE);
			cross4 = (ImageView)this.findViewById(R.id.cross4);			
			cross4.setVisibility(View.INVISIBLE);
			cross5 = (ImageView)this.findViewById(R.id.cross5);			
			cross5.setVisibility(View.INVISIBLE);
			cross6 = (ImageView)this.findViewById(R.id.cross6);			
			cross6.setVisibility(View.INVISIBLE);
			cross7 = (ImageView)this.findViewById(R.id.cross7);			
			cross7.setVisibility(View.INVISIBLE);
			cross8 = (ImageView)this.findViewById(R.id.cross8);			
			cross8.setVisibility(View.INVISIBLE);
			cross9 = (ImageView)this.findViewById(R.id.cross9);			
			cross9.setVisibility(View.INVISIBLE);
			circle1 = (ImageView)this.findViewById(R.id.circle1);			
			circle1.setVisibility(View.INVISIBLE);
			circle2 = (ImageView)this.findViewById(R.id.circle2);			
			circle2.setVisibility(View.INVISIBLE);
			circle3 = (ImageView)this.findViewById(R.id.circle3);			
			circle3.setVisibility(View.INVISIBLE);
			circle4 = (ImageView)this.findViewById(R.id.circle4);			
			circle4.setVisibility(View.INVISIBLE);
			circle5 = (ImageView)this.findViewById(R.id.circle5);			
			circle5.setVisibility(View.INVISIBLE);
			circle6 = (ImageView)this.findViewById(R.id.circle6);			
			circle6.setVisibility(View.INVISIBLE);
			circle7 = (ImageView)this.findViewById(R.id.circle7);			
			circle7.setVisibility(View.INVISIBLE);
			circle8 = (ImageView)this.findViewById(R.id.circle8);			
			circle8.setVisibility(View.INVISIBLE);
			circle9 = (ImageView)this.findViewById(R.id.circle9);			
			circle9.setVisibility(View.INVISIBLE);

			//Get height and width of each grid element
			//Assume the board is 3X3
			gridElementX = 100F;
			gridElementY = 100F;

			delta = 5F;
		}
		else if(gameType.contentEquals(Strings.checkers))
		{
			//boardImage.setImageResource(R.drawable.checkersboard);
		}
		else if(gameType.contentEquals(Strings.chess))
		{
			//boardImage.setImageResource(R.drawable.chessboard);
		}
		else if(gameType.contentEquals(Strings.connect4))
		{
			//boardImage.setImageResource(R.drawable.connect4board);
		}												

		boardImage.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {

				final float currX = event.getX();
				final float currY = event.getY();

				//If finger has been dragged, return without doing anything
				//if(Math.abs(currX - downX) > gridElementX)
				//return false;
				//else if(Math.abs(currY - downY) > gridElementY)
				//return false;

				int gameX =  0;
				int gameY = 0; 

				//Get the game grid-based coordinates
				if(currX >= gridElementX*0 && currX <= gridElementX*1)
				{
					gameX = 0;						
				}
				else if(currX >= (gridElementX*1)+(delta*1) && currX <= (gridElementX*2)+(delta*1))
				{
					gameX = 1;						
				}
				else if(currX >= (gridElementX*2)+(delta*2) && currX <= (gridElementX*3)+(delta*2))
				{
					gameX = 2;						
				}

				if(currY >= gridElementY*0 && currY <= gridElementY*1)
				{
					gameY = 0;					
				}
				else if(currY >= (gridElementY*1)+(gridElementY*1) && currY <= (gridElementY*2)+(delta*1))
				{
					gameY = 1;
				}
				else if(currY >= (gridElementY*2)+(delta*2) && currY <= (gridElementY*3)+(delta*2))
				{
					gameY = 2;
				}
				else
					return false;

				coordinates.clear();//clear list for tic tac toe
				coordinates.add(new Coordinate(gameX, gameY));
				displayPiece(gameX, gameY);
				return true;				
			}			
		});
	}	
}
