package org.swat.client.userinterface;

import java.util.ArrayList;
import java.util.List;

import org.swat.client.R;
import org.swat.client.control.Control;
import org.swat.data.Coordinate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class GameScreen extends Activity
{
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// GUI functions
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		

		LayoutParams params = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		RelativeLayout mainLayout = new RelativeLayout(GameScreen.this);
		mainLayout.setLayoutParams( new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		DrawView drawView = new DrawView(GameScreen.this);
		drawView.setImages(R.drawable.o, R.drawable.x, R.drawable.tictactoeboard);
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
			@Override
			public void onClick(View v) {
				if(Control.okToSubmitMove)
				{
					//Create list of clicked coordinates
					List<Coordinate>coords = new ArrayList<Coordinate>();
					coords.add(Control.getClickedLocation());
					//Submit move to server via control
					Control.makeMove(coords);
					
					//Alert user as to what happened
					AlertDialog.Builder b = new AlertDialog.Builder(GameScreen.this);
					b.setMessage(Strings.submitMove);
					b.setPositiveButton("OK", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface arg0, int arg1) {													
						}						
					});
					b.show();
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
