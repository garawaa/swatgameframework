package org.swat.client;

import java.util.List;

import android.app.Activity;
import android.content.Intent;

public class ClientControl extends Activity
{
	/*Methods to be used:
retrieveDeployedGames :
retrieveOpenGames :
createGame :
joinGame :
retrieveMyGame :
makeMove :
retrieveGameState :*/
	
	private Communication comm;
	private static final int ACTIVITY_CREATE = 1;
	
	public ClientControl(Communication c)
	{
		comm = c;
	}
	
	public void retrieveDeployedGames(){
		List<String> gameList = comm.retrieveDeployedGames();
		GameListView gLV = new GameListView(gameList);
		
		Intent gameListView = new Intent(ClientControl.this, GameListView.class);										
		startActivityForResult(gameListView, ACTIVITY_CREATE);
	}

	public void retrieveOpenGames() {
		comm.retrieveOpenGames();
	}

	public void retrieveMyGames() {
		comm.retrieveMyGames();
	}

	public void createNewGame() {
		comm.createNewGame();
	}
}