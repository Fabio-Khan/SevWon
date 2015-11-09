package com.example.pandamonium.sevenw.Storico;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pandamonium.sevenw.New_Game.Game_Match.Match_Row_Adapter;
import com.example.pandamonium.sevenw.New_Game.Players_Selection.ChosePlayers;
import com.example.pandamonium.sevenw.R;
import com.example.pandamonium.sevenw.Support.Database.DBManager;
import com.example.pandamonium.sevenw.Support.Database.DBStrings;
import com.example.pandamonium.sevenw.Support.Match;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 14/10/2015.
 */
public class StoricoPartite extends AppCompatActivity {

    private ListView listview;
    private Storico_Adapter adapter;
    private DBManager db;
    private ArrayList<Match> history = new ArrayList<>();
    private Menu menu;
    private boolean toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_match);

        toggle = false;

        db = new DBManager(this);
        history = db.query_history();

        adapter = new Storico_Adapter(this,history,db);
        listview = (ListView) findViewById(R.id.history_list);
        listview.setAdapter(adapter);

    }

    /* Gestione della Action Bar */
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.teams_toggle, menu);
        return true;
    }

    /* Quando viene premuto un pulsante della Action Bar */
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //sono passate le espansioni selezionate  e il num di giocatori max
        switch(id){
            case R.id.team_toggle:
                if (!toggle) {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.menu_teams));
                    toggle = true;
                    history = db.query_history_teams();
                    adapter.set_history(history);
                    adapter.set_teams(toggle);
                    adapter.notifyDataSetChanged();

                }
                else{
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.menu_single));
                    toggle = false;
                    history = db.query_history();
                    adapter.set_history(history);
                    adapter.set_teams(toggle);
                    adapter.notifyDataSetChanged();
                }

                return true;
            default:
                Toast.makeText(getApplicationContext(), "MENU ERROR", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }


}
