package com.example.pandamonium.sevenw.New_Game.Players_Selection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandamonium.sevenw.New_Game.Game_Match.Match_Activity;
import com.example.pandamonium.sevenw.Support.Database.DBManager;
import com.example.pandamonium.sevenw.Support.Player;
import com.example.pandamonium.sevenw.R;
import com.example.pandamonium.sevenw.Support.Profile;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 21/09/2015.
 */

public class ChosePlayers extends AppCompatActivity {

    // Array di giocatori disponibili alla scelta.
    private ArrayList<Player> players = new ArrayList<>();

    // Lista delle Espansioni scelte
    private ArrayList<String> exps = new ArrayList<>();

    //giocatori ammessi a seconda delle espansioni
    private int allowed_players;
    private int doppia;

    //tiene traccia della modalità team
    private boolean team_btn;

    // Supporto all'activity.
    private TextView textview;
    private GridView gridview;
    private GridAdapter_Players grid;
    private BroadcastReceiver receiver;

    //Per il pool di giocatori.
    private DBManager db;
    private ArrayList<Profile> profiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_plsel);

        // Riceve la lista di espansioni selezionate
        Intent intent = getIntent();
        exps = intent.getStringArrayListExtra("expansions");

        // Costruisce la lista dei giocatori disponibili
        db = new DBManager(this);
        build_players();
        allowed_players = intent.getIntExtra("maxP", 0);
        team_btn = false;

        textview = (TextView) findViewById(R.id.players_sel_number);
        textview.setText("Players: 0/" + allowed_players);

        gridview = (GridView)findViewById(R.id.players_grid);
        grid = new GridAdapter_Players(this, players, allowed_players,team_btn);
        gridview.setAdapter(grid);

        //settaggio receiver, utilizzato per aggiornare text alla selezione in grid
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // Quando coglie un messaggio con questo tag
                if (intent.getAction().equals(GridAdapter_Players.INTENT_ACTION)){
                    //Recupero dell'intent, con extra
                    textview.setText("Players: " + intent.getIntExtra(GridAdapter_Players.INTENT_EXTRA, 0) + "/" + allowed_players);
                }
                else{
                    Toast.makeText(getApplicationContext(), "ERROR!!", Toast.LENGTH_SHORT).show();
                }
            }
        };

        //Registrazione
        IntentFilter filter = new IntentFilter();
        filter.addAction(GridAdapter_Players.INTENT_ACTION);
        this.getApplicationContext().registerReceiver(this.receiver, filter);

    }


    /* Gestione CheckBox */
    public void onCheckboxClicked(View view) {

        CheckBox chk = (CheckBox) view;

        if (chk.isChecked()){
            team_btn = true;
            if (allowed_players == 9){
                doppia = allowed_players;
                allowed_players = 8;
            }
        }
        else{
            team_btn = false;
            if (doppia ==9)
                allowed_players = doppia;
        }

        Reset_Choses();

        //aggiorna adapter
        grid.setTeams(team_btn);
        grid.notifyDataSetChanged();
    }


    /* Gestione della Action Bar */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_btn, menu);
        return true;
    }

    /* Quando viene premuto un pulsante della Action Bar */
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id){
            case R.id.next_btn:

                if (Chosen_Players().isEmpty()||Chosen_Players().size() == 1){
                    Toast.makeText(getApplicationContext(), "Chose at least two players!", Toast.LENGTH_SHORT).show();
                }

                else if (team_btn && grid.getCT1() == 1){
                    Toast.makeText(getApplicationContext(), "Team1 has only one player!", Toast.LENGTH_SHORT).show();
                }

                else if (team_btn && grid.getCT2() == 1){
                    Toast.makeText(getApplicationContext(), "Team2 has only one player!", Toast.LENGTH_SHORT).show();
                }

                else if (team_btn && grid.getCT3() == 1){
                    Toast.makeText(getApplicationContext(), "Team3 has only one player!", Toast.LENGTH_SHORT).show();
                }

                else if (team_btn && grid.getCT4() == 1){
                    Toast.makeText(getApplicationContext(), "Team4 has only one player!", Toast.LENGTH_SHORT).show();
                }

                else if (grid.getCT1() + grid.getCT2() + grid.getCT3() + grid.getCT4() <= 2 && team_btn){
                    Toast.makeText(getApplicationContext(), "Chose at least two teams!", Toast.LENGTH_SHORT).show();
                }

                else {
                    Intent intent = new Intent(this, Match_Activity.class);
                    intent.putStringArrayListExtra("expansions", exps);
                    intent.putStringArrayListExtra("players", Chosen_Players());
                    intent.putIntegerArrayListExtra("teams", Chosen_Teams());
                    startActivity(intent);
                }

                return true;

            default:
                Toast.makeText(getApplicationContext(), "next button error", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }


    /* Qui vengono raccolti tutti i giocatori attualmente disponibili */
    private void build_players() {

        profiles = db.query_players();

        for (int i=0; i<profiles.size(); i++){
            players.add(new Player(false, profiles.get(i).getName(), R.mipmap.player));
        }

    }


    /* Dato l'insieme di tutti i giocatori, ritorna quelli scelti. */
    private ArrayList<String> Chosen_Players(){

        ArrayList<String> Chosen = new ArrayList<>();

        for (int i=0; i<players.size(); i++){
            if (players.get(i).isSelected()){
                Chosen.add(players.get(i).getName());
            }
        }

        return Chosen;
    }


    /* Restituisce i teams dei giocatori scelti. */
    private ArrayList<Integer> Chosen_Teams(){

        ArrayList<Integer> Chosen = new ArrayList<>();

        for (int i=0; i<players.size(); i++){
            if (players.get(i).isSelected()){
                Chosen.add(players.get(i).getTeam());
            }
        }

        return Chosen;
    }


    private void Reset_Choses(){
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setSelected(false);
            players.get(i).setTeam(0);
        }

        //resetta contatori delle selezioni
        grid.Reset_P();

        textview.setText("Players: 0/" + allowed_players);
    }

}