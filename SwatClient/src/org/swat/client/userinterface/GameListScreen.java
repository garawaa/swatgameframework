package org.swat.client.userinterface;

import org.swat.client.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GameListScreen extends ListActivity
{		
	/**
	 * 
	 */
	private String listType;
	private String [] gameList;

	//TODO add create game menu in case of deployed games list 

	/* (non-Javadoc)
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	public void onListItemClick(ListView l, View v, final int position, long id)
	{						
		try
		{
			AlertDialog.Builder dialog = new AlertDialog.Builder(GameListScreen.this);
			dialog.setTitle(R.string.gameSure);
			dialog.setNegativeButton(R.string.alert_dialog_cancel, new OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {				
					//On cancel, do nothing 
				}			
			});

			if(listType.contentEquals(Strings.deployedGames))
			{
				//Start new game - assume its Tic Tac Toe for now			
				dialog.setPositiveButton(R.string.alert_dialog_start_game, new OnClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int menuOption) {										
						//Start game from control
						//TODO Control.createNewGame(gameList[position]);										

						//Display game screen
						Intent intent = new Intent(GameListScreen.this, GameScreen.class);
						startActivityForResult(intent, RequestCodes.gameScreenRequestCode);
					}				
				});			
				dialog.show();
			}
			else if(listType.contentEquals(Strings.myGames))
			{
				//Join game
				dialog.setPositiveButton(R.string.alert_dialog_join_game, new OnClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int menuOption) {										
						//Join game from control
						//TODO int gameID = Control.getCurrentMyGames().get(position).getGameID();
						//Control.joinGame(gameID);			

						//Display game screen
						Intent intent = new Intent(GameListScreen.this, GameScreen.class);
						startActivityForResult(intent, RequestCodes.gameScreenRequestCode);
					}				
				});			
				dialog.show();
			}
			else if(listType.contentEquals(Strings.openGames))
			{
				//Join game
				dialog.setPositiveButton(R.string.alert_dialog_join_game, new OnClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int menuOption) {										
						//Join game from control
						//TODO int gameID = Control.getCurrentOpenGames().get(position).getGameID();
						//Control.joinGame(gameID);										

						//Display game screen
						Intent intent = new Intent(GameListScreen.this, GameScreen.class);
						startActivityForResult(intent, RequestCodes.gameScreenRequestCode);
					}				
				});			
				dialog.show();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/** Called when the activity is first created. */
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		Bundle b = getIntent().getExtras();
		gameList = b.getStringArray(Strings.gameList);        
		listType = b.getString(Strings.listType);

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, gameList));
		getListView().setTextFilterEnabled(true);
	}	
}
