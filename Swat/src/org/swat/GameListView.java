package org.swat.client;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class GameListView extends ListActivity
{
	List<String> gameList;
	
	/** Constructors **/
	public GameListView(List<String> gL)
	{
		gameList = gL;
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelistview);
                
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, gameList));
        getListView().setTextFilterEnabled(true);

    }
}
