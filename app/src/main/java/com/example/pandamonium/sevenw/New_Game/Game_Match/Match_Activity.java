package com.example.pandamonium.sevenw.New_Game.Game_Match;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pandamonium.sevenw.Support.Database.DBManager;
import com.example.pandamonium.sevenw.Support.Match;
import com.example.pandamonium.sevenw.Support.Player;
import com.example.pandamonium.sevenw.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Pandamonium on 01/10/2015.
 */
public class Match_Activity extends AppCompatActivity {

    // Espansioni Selezionate
    private ArrayList<String> exps = new ArrayList<>();

    // Giocatori Selezionati
    private ArrayList<String> sel_players = new ArrayList<>();

    // Team di ogni giocatore (in corrispondenza indiciale con sel_players)
    private ArrayList<Integer> teams = new ArrayList<>();

    // Nuovo array di giocatori, creato solo con quelli selezionati.
    private ArrayList<Player> players = new ArrayList<>();

    //Struttura Dati del Match
    private Match match = new Match();

    //Database
    private DBManager db;

    //Activity Support
    private ListView listview;
    private Match_Row_Adapter row_adapter;
    private Match_Row_Adapter_Teams row_adapter_teams;

    public static final Integer REQUEST_MATCH = 1;
    public static final Integer REQUEST_TEAMS = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        exps = intent.getStringArrayListExtra("expansions");
        sel_players = intent.getStringArrayListExtra("players");
        teams = intent.getIntegerArrayListExtra("teams");

        match.setDate(get_date());

        db = new DBManager(this);

        //Check teams
        int k=0;
        for (int i=0; i<teams.size(); i++) k=k+teams.get(i);

        //No teams
        if (k == 0){
            setContentView(R.layout.game_match);
            for (int i=0; i<sel_players.size(); i++){
                Player pippo = new Player(true, sel_players.get(i), 0);
                pippo.setTeam(teams.get(i));
                players.add(pippo);
            }

            match.setTeams(false);
            row_adapter = new Match_Row_Adapter(this, players, exps);
            listview = (ListView) findViewById(R.id.match_list);
            listview.setAdapter(row_adapter);
        }

        /* Teams, vanno da 1 a 4. Riordina lista per teams. */
        else{
            setContentView(R.layout.game_match_teams);
            for (int i=1; i<=4; i++){
                for (int j=0; j<sel_players.size(); j++){
                    if(teams.get(j)==i) {
                        Player pippo = new Player(true, sel_players.get(j), 0);
                        pippo.setTeam(teams.get(j));
                        players.add(pippo);
                    }
                }
            }

            match.setTeams(true);
            row_adapter_teams = new Match_Row_Adapter_Teams(this, players, exps);
            listview = (ListView) findViewById(R.id.match_list_teams);
            listview.setAdapter(row_adapter_teams);
        }

    }


    /* Passa all'adapter l'evento di ritorno dell'activity chiamata */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_MATCH)
            row_adapter.onActivityResult(requestCode, resultCode, data);
        else if (requestCode == REQUEST_TEAMS)
            row_adapter_teams.onActivityResult(requestCode, resultCode, data);
    }


    /* Gestione della Action Bar */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_btn, menu);
        return true;
    }

    /* Quando viene premuto un pulsante della Action Bar */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.savebtn:

                boolean AllDone = true;

                for (int i=0; i<players.size(); i++){
                    if (players.get(i).getTOT()==0)
                        AllDone = false;
                }

                // Se tutti hanno un punteggio assegnato
                if (AllDone){

                    match.setPlayers(players);

                    if (match.getTeams()){
                        db.save_match_teams(match);
                    }
                    else {
                        db.save_match(match);
                    }

                    Toast.makeText(getApplicationContext(), "Match Saved!", Toast.LENGTH_SHORT).show();
                    //TODO Torna al menù principale

                }
                else{
                    Toast.makeText(getApplicationContext(), "Score Missing!!", Toast.LENGTH_SHORT).show();
                }

                return true;

            default:
                Toast.makeText(getApplicationContext(), "next button error", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }


    public Integer get_date(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = df.format(calendar.getTime());
        Integer data = Integer.parseInt(formattedDate);

        return data;

    }

}
