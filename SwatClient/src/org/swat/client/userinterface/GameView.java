package org.swat.client.userinterface;

import org.swat.client.control.ClientController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * @author Abhi Keshav
 *
 */
public class GameView extends View {
	/**
	 * 
	 */
	private static PieceImage [] crossPieces; 
	private static PieceImage [] circlePieces;

	private int boardX;
	private int boardY;

	private Context context;

	/**
	 * @param context The Context of the Activity
	 */
	public GameView(Context context) {
		super(context);
		setFocusable(true); //necessary for getting the touch events
		crossPieces = new PieceImage[9];
		circlePieces = new PieceImage[9];
		this.context = context;
	}

	// the method that draws the balls
	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
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

	/**
	 * 
	 */
	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
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
			if(ClientController.getCurrentTurnPlayerID() == ClientController.getThisPlayerID())
			{
				if(ClientController.setClickedLocation(boardX, boardY))
				{
					float pieceX = ClientController.getClickedLocation().getX()*((float)ClientController.boardWidth/3f);
					float pieceY = ClientController.getClickedLocation().getY()*((float)ClientController.boardHeight/3f);
					//Assume "this" player is always cross //test
					if(ClientController.crossIndex >= 5)
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
					crossPieces[ClientController.crossIndex].setX((int)pieceX); //test
					crossPieces[ClientController.crossIndex].setY((int)pieceY);
					//redraw board
					invalidate();
				}
			}
			break; 
		} 		
		return true; 	
	}

	
	/**
	 * Run animation
	 * @param ctx Context
	 * @param animDirection Direction of animation
	 * @return The animation
	 */
	public Animation runAnimation(Activity ctx, int animDirection) {
		Animation animation = AnimationUtils.loadAnimation(ctx,
				animDirection);		
		animation.setDuration(1000);
		animation.setStartTime(AnimationUtils.currentAnimationTimeMillis());		
		this.startAnimation(animation);			
		return animation;
	}

	/**Set images for the crosses and circles
	 * @param img1 Image resource for the cross
	 * @param img2 Image resource for the circle
	 */
	public void setImages(int img1, int img2)
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
