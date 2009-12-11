package org.swat.client.userinterface;

import java.util.ArrayList;
import java.util.List;

import org.swat.client.R;
import org.swat.client.control.ClientController;
import org.swat.data.Coordinate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class GameScreen extends Activity
{
	/**Called when back button is hit**/
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_BACK:
			AlertDialog.Builder b = new AlertDialog.Builder(GameScreen.this);
			b.setMessage("Game will be saved on server");
			b.show();
			GameScreen.this.finish();			
			//TODO save game on server
			return true;			
		}
		return false;		
	}
	
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// GUI functions
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

	/** Called when the activity is first created. */
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		

		LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		RelativeLayout mainLayout = new RelativeLayout(GameScreen.this);
		mainLayout.setLayoutParams( new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		ImageView boardImage = new ImageView(GameScreen.this);
		boardImage.setImageResource(R.drawable.tictactoeboard);		
		//LayoutParams boardParams = new LayoutParams(Control.boardWidth, Control.boardHeight);
		//LayoutParams boardParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		LayoutParams boardParams = new LayoutParams(ClientController.boardWidth, ClientController.boardHeight);
		boardParams.leftMargin = 0;
		boardParams.topMargin = 0;
		boardImage.setLayoutParams(boardParams);
		RelativeLayout boardLayout = new RelativeLayout(GameScreen.this);
		boardLayout.setLayoutParams(boardParams);
		//boardLayout.setLayoutParams(params);
		boardLayout.addView(boardImage);
		mainLayout.addView(boardLayout);
		
		GameView drawView = new GameView(GameScreen.this);
		drawView.setImages(R.drawable.o, R.drawable.x);
		LayoutParams drawViewParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//drawViewParams.leftMargin = 0;
		//drawViewParams.topMargin = 0;		
		drawView.setLayoutParams(drawViewParams);
		RelativeLayout drawViewLayout = new RelativeLayout(GameScreen.this);
		drawViewLayout.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		drawViewLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		drawViewLayout.addView(drawView);
		mainLayout.addView(drawViewLayout);

		Button submitButton = new Button(GameScreen.this);
		LayoutParams buttonParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		buttonParams.leftMargin = 100;
		buttonParams.topMargin = 320;
		submitButton.setLayoutParams(buttonParams);
		submitButton.setText("Submit Move");
		RelativeLayout buttonLayout = new RelativeLayout(GameScreen.this);
		buttonLayout.setLayoutParams(params);
		buttonLayout.addView(submitButton);
		mainLayout.addView(buttonLayout);

		setContentView(mainLayout);

		submitButton.setOnClickListener(new OnClickListener(){
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			@Override
			public void onClick(View v) {
				if(ClientController.okToSubmitMove)
				{
					//Create list of clicked coordinates
					List<Coordinate>coords = new ArrayList<Coordinate>();
					coords.add(ClientController.getClickedLocation());
					//Submit move to server via control
					ClientController.makeMove(coords);
					
					//Alert user as to what happened
					AlertDialog.Builder b = new AlertDialog.Builder(GameScreen.this);
					b.setMessage(Strings.submitMove);
					b.setPositiveButton("OK", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface arg0, int arg1) {													
						}						
					});
					b.show();
					//After move is made, increment cross index
					ClientController.crossIndex+=1;
					//TODO get other player's move and display it
				}
				else
				{
					//Alert user as to what happened
					AlertDialog.Builder b = new AlertDialog.Builder(GameScreen.this);
					b.setMessage(Strings.submitMoveNot);
					b.setPositiveButton("OK", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface arg0, int arg1) {													
						}						
					});
					b.show();
				}
			}			
		});
	}	
}
