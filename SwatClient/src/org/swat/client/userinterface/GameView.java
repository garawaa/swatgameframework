package org.swat.client.userinterface;

import org.swat.client.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View
{
	private Piece[] colorballs = new Piece[3]; // array that holds the balls
	private int balID = 0; // variable to know what ball is being dragged

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		setFocusable(true); //necessary for getting the touch events

		// setting the start point for the balls
		Point point1 = new Point();
		point1.x = 50;
		point1.y = 20;
		Point point2 = new Point();
		point2.x = 100;
		point2.y = 20;

		// declare each ball with the ColorBall class
		colorballs[0] = new Piece(context,R.drawable.o);
		colorballs[1] = new Piece(context,R.drawable.x);        
	}
	// the method that draws the balls
	@Override protected void onDraw(Canvas canvas) {
		//canvas.drawColor(0xFFCCCCCC);     //if you want another background color       

		for (Piece ball : colorballs) {
			canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(), null);
		}
	}

	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction(); 

		int X = (int)event.getX(); 
		int Y = (int)event.getY(); 

		switch (eventaction ) { 
		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball
			balID = 0;
			for (Piece ball : colorballs) {
				// check if inside the bounds of the ball (circle)
				// get the center for the ball
				int centerX = ball.getX() + 25;
				int centerY = ball.getY() + 25;

				// calculate the radius from the touch to the center of the ball
				double radCircle  = Math.sqrt( (double) (((centerX-X)*(centerX-X)) + (centerY-Y)*(centerY-Y)));

				// if the radius is smaller then 23 (radius of a ball is 22), then it must be on the ball
				if (radCircle < 23){
					balID = ball.getID();
					break;
				}
			}

			break; 

		case MotionEvent.ACTION_MOVE:   // touch drag with the ball
			// move the balls the same as the finger
			if (balID > 0) {
				colorballs[balID-1].setX(X-25);
				colorballs[balID-1].setY(Y-25);
			}      	
			break; 

		case MotionEvent.ACTION_UP: 
			// touch drop - just do things here after dropping
			break; 
		} 
		// redraw the canvas
		invalidate(); 
		return true; 	
	}
}
