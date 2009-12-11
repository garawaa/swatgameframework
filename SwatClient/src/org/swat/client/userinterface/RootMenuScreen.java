package org.swat.client.userinterface;

import java.util.List;

import org.swat.client.R;
import org.swat.client.control.ClientController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class RootMenuScreen extends Activity 
{
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Menu item constants
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	private static final int depGames = Menu.FIRST + 1;
	private static final int openGames = Menu.FIRST + 2;
	private static final int myGames = Menu.FIRST + 3;

	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Private members
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	private AlertDialog.Builder userAuthDialog;
	private String userEmail;
	private String userPassword;
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Menu-related functions
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	/** create the menu items */
	/**
	 * @param menu The menu for which buttons are created
	 */
	private void populateMenu(Menu menu) {

		// enable keyb shortcuts, qwerty mode = true means only show keyb shortcuts (not numeric) and vice versa
		// these only show up in context menu, not options menu
		menu.setQwertyMode(true);

		MenuItem item1 = menu.add(0, depGames, 0, Strings.retDepGames);
		{
			item1.setAlphabeticShortcut('d');
			item1.setIcon(android.R.drawable.ic_menu_manage);
		}	

		MenuItem item2 = menu.add(0, openGames, 0, Strings.retOpenGames);
		{
			item2.setAlphabeticShortcut('o');
			item2.setIcon(android.R.drawable.ic_menu_manage);
		}

		MenuItem item3 = menu.add(0, myGames, 0, Strings.retMyGames);
		{
			item3.setAlphabeticShortcut('m');
			item3.setIcon(android.R.drawable.ic_menu_manage);
		}
	}

	/**respond to menu item selection
	 * @param item Menu items
	 * @return true or false depending on button clicked
	 */
	private boolean applyMenuChoice(MenuItem item) {		
		switch (item.getItemId()) {
		case depGames:
			//Retrieve deployed games
			startListActivity(ClientController.retrieveDeployedGames(), Strings.deployedGames);
			return true;
		case openGames:
			//Retrieve open games
			startListActivity(ClientController.retrieveOpenGames(), Strings.openGames);
			return true;
		case myGames:
			//Retrieve my games
			startListActivity(ClientController.retrieveMyGames(), Strings.myGames);
			return true;	    
		}
		return false;
	}

	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Menu button - option menu
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

	/** hook into menu button for activity */
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		populateMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}

	/** when menu button option selected */
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override public boolean onOptionsItemSelected(MenuItem item) {
		return applyMenuChoice(item) || super.onOptionsItemSelected(item);
	}

	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Actions based on control output
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

	/**Starts ListActivity to display list of games
	 * @param list List of game names
	 * @param type Type of list (deployed, my, join games)
	 */
	private void startListActivity(List<String> list, String type)
	{		
		Intent gameListView = new Intent(RootMenuScreen.this, GameListScreen.class);
		Bundle b = new Bundle();
		b.putStringArray(Strings.gameList, (String[])list.toArray(new String[0]));
		b.putString(Strings.listType, type);		
		gameListView.putExtras(b);		
		startActivityForResult(gameListView, RequestCodes.gameListScreenRequestCode);
	}

	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	// Overriden functions
	//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

	/** Called when the activity is first created. */
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rootmenuscreen);

		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.alert_dialog_text_entry, null);

		final EditText password_field = (EditText)textEntryView.findViewById(R.id.password_edit);
		final EditText email_field = (EditText)textEntryView.findViewById(R.id.username_edit);

		userAuthDialog = new AlertDialog.Builder(RootMenuScreen.this);
		userAuthDialog.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle(R.string.alert_dialog_text_entry)
		.setView(textEntryView);
		userAuthDialog.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				/* User clicked OK so do some stuff */
				userEmail = email_field.getEditableText().toString();
				userPassword = password_field.getEditableText().toString();
			}
		});
		userAuthDialog.setNeutralButton(R.string.alert_dialog_register, new DialogInterface.OnClickListener() {				
			@Override
			public void onClick(DialogInterface dialog, int which) {					
				/* User clicked OK so do some stuff */
				userEmail = email_field.getEditableText().toString();
				userPassword = password_field.getEditableText().toString();
			}
		});
		userAuthDialog.show();         
	}
}