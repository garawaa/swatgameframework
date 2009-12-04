package org.swat.client.userinterface;

import org.swat.client.control.Control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class GameView extends View {
	private static PieceImage [] crossPieces; 
	private static PieceImage [] circlePieces;

	private int boardX;
	private int boardY;

	private Context context;

	public GameView(Context context) {
		super(context);
		setFocusable(true); //necessary for getting the touch events
		crossPieces = new PieceImage[9];
		circlePieces = new PieceImage[9];
		this.context = context;
	}

	// the method that draws the balls
	@Override protected void onDraw(Canvas canvas) {
		//canvas.drawColor(0xFFCCCCCC);     //if you want another background color       

		try
		{
			//draw the cards on the canvas
			for(int i = 0; i<9; i++)
			{				
				canvas.drawBitmap(crossPieces[i].getBitmap(), crossPieces[i].getX(), crossPieces[i].getY(), null);				
				canvas.drawBitmap(circlePieces[i].getBitmap(), circlePieces[i].getX(), circlePieces[i].getY(), null);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction(); 		

		switch (eventaction ) { 
		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball				
			break; 

		case MotionEvent.ACTION_MOVE:   // touch drag with the ball		
			//For tic tac toe, there is no dragging
			//so not doing anything here
			break; 

		case MotionEvent.ACTION_UP: 
			// TODO touch drop - place 'X' or 'O' at the proper location
			boardX = (int)event.getX();
			boardY = (int)event.getY();
			//If its this player's turn, send the clicked coordinates to control
			if(Control.getCurrentTurnPlayerID() == Control.getThisPlayerID())
			{
				if(Control.setClickedLocation(boardX, boardY))
				{
					float pieceX = Control.getClickedLocation().getX()*((float)Control.boardWidth/3f);
					float pieceY = Control.getClickedLocation().getY()*((float)Control.boardHeight/3f);
					//Assume "this" player is always cross //test
					if(Control.crossIndex >= 5)
					{
						AlertDialog.Builder b = new AlertDialog.Builder(context);
						b.setMessage("End of game");
						b.setPositiveButton("OK", new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface arg0, int arg1) {													
							}						
						});
						b.show();
						break;
					}
					crossPieces[Control.crossIndex].setX((int)pieceX); //test
					crossPieces[Control.crossIndex].setY((int)pieceY);
					//redraw board
					invalidate();
				}
			}
			break; 
		} 		
		return true; 	
	}

	/**Animates next/previous card motion
	 * @param ctx context**/
	public Animation runAnimation(Activity ctx, int animDirection) {
		Animation animation = AnimationUtils.loadAnimation(ctx,
				animDirection);		
		animation.setDuration(1000);
		animation.setStartTime(AnimationUtils.currentAnimationTimeMillis());		
		this.startAnimation(animation);			
		return animation;
	}

	/**Set images for the three cards**/
	public void setImages(int img1, int img2, int img3)
	{			
		for(int i = 0; i<9 ; i++)
		{
			crossPieces[i] = new PieceImage(context);
			circlePieces[i] = new PieceImage(context);

			crossPieces[i].setImage(img1, -320, 0, 100, 100);	
			circlePieces[i].setImage(img2, -320, 00, 100, 100);
		}
		invalidate();
	}
}
