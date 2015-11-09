package com.example.pandamonium.sevenw.Statistics;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.pandamonium.sevenw.New_Game.Expansions_Selection.ExpansionSelection;
import com.example.pandamonium.sevenw.New_Game.Expansions_Selection.ListAdapter;
import com.example.pandamonium.sevenw.New_Game.Players_Selection.GridAdapter_Players;
import com.example.pandamonium.sevenw.Players.Main_Players;
import com.example.pandamonium.sevenw.R;
import com.example.pandamonium.sevenw.Storico.StoricoPartite;
import com.example.pandamonium.sevenw.Support.Database.DBManager;
import com.example.pandamonium.sevenw.Support.Match;
import com.example.pandamonium.sevenw.Support.Profile;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 14/10/2015.
 */
public class Main_Statistics extends AppCompatActivity {

    public static final Integer MAX = 1;
    public static final Integer MIN = 2;
    public static final Integer AVG = 3;

    private DBManager db;
    private ArrayList<Profile> players = new ArrayList<>();

    /* Categories */
    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<String> plays = new ArrayList<>();
    private ArrayList<Integer> scores = new ArrayList<>();
    private ArrayList<Integer> dates = new ArrayList<>();

    /* scores */
    private ArrayList<String> pl = new ArrayList<>();
    private ArrayList<Integer> sco = new ArrayList<>();
    private ArrayList<Integer> dat = new ArrayList<>();

    private ListView listView;
    private Statistics_listAdapter players_adapter;
    private Statistics_Categories categories_adapter;
    private Statistics_Scores scores_adapter;

    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.statistics_matches);

        categories_init();
        mode =MAX;

        db = new DBManager(this);
        players = db.query_players();

        for (int i=0;i<players.size();i++){
            players.get(i).setMatches(db.query_playermatch(players.get(i).getName(),1));
            players.get(i).setMatches_team(db.query_playermatch_teams(players.get(i).getName(),1));
        }
        initial_calcs();
        players = victories_sort(players);
        categories_calc();
        scores_calc(mode);

        players_adapter = new Statistics_listAdapter(this, players);
        categories_adapter = new Statistics_Categories(this, categories, plays, scores, dates);
        scores_adapter = new Statistics_Scores(this, pl, sco, dat);

        listView = (ListView)findViewById(R.id.statistic_list);
        listView.setAdapter(players_adapter);

        // Riferimenti ai bottoni
        Button btn1 = (Button)findViewById(R.id.stat_players);
        Button btn2 = (Button)findViewById(R.id.stat_scores);
        final Button btn3 = (Button)findViewById(R.id.stat_maxscore);
        final Button btn4 = (Button)findViewById(R.id.stat_displaymode);

        /* Mostra classifica giocatori in base alle partite vinte.*/
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                listView.setAdapter(players_adapter);
            }
        });


        /* Mostra classifica categorie con i punteggi più alti e i rispettivi giocatgori.*/
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                listView.setAdapter(categories_adapter);
            }
        });


        /* Mostra classifica punteggi totali. Il più alto e la media a giocatore.*/
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){

                PopupMenu popup = new PopupMenu(getApplicationContext(),btn3);

                popup.getMenu().add(1,1,1,"Highest");
                popup.getMenu().add(1,2,2,"Lowest");
                popup.getMenu().add(1, 3, 3, "Average");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case 1:
                                btn3.setText(getResources().getString(R.string.Stat_btn3));
                                mode = MAX;
                                break;
                            case 2:
                                btn3.setText(getResources().getString(R.string.Stat_btn4));
                                mode = MIN;
                                break;
                            case 3:
                                btn3.setText(getResources().getString(R.string.Stat_btn5));
                                mode = AVG;
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "MENU ERROR", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        scores_calc(mode);
                        listView.setAdapter(scores_adapter);
                        scores_adapter.setPlayer(pl);
                        scores_adapter.setScore(sco);
                        scores_adapter.setDates(dat);
                        scores_adapter.notifyDataSetChanged();

                        return true;
                    }

                });

                popup.show();

            }
        });


        /* Queste opzioni si incrociano a quelle dei bottoni, mostrando subset di dati.*/
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                PopupMenu popup = new PopupMenu(getApplicationContext(),btn4);

                popup.getMenu().add(1,1,1,"All");
                popup.getMenu().add(1,2,2,"2 Players");
                popup.getMenu().add(1,3,3,"3 Players");
                popup.getMenu().add(1,4,4,"4 Players");
                popup.getMenu().add(1,5,5,"5 Players");
                popup.getMenu().add(1,6,6,"6 Players");
                popup.getMenu().add(1,7,7,"7 Players");
                popup.getMenu().add(1,8,8,"8 Players");
                popup.getMenu().add(1,9,9,"9 Players");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case 1:
                                btn4.setText("All");
                                break;
                            case 2:
                                btn4.setText("2 Players");
                                break;
                            case 3:
                                btn4.setText("3 Players");
                                break;
                            case 4:
                                btn4.setText("4 Players");
                                break;
                            case 5:
                                btn4.setText("5 Players");
                                break;
                            case 6:
                                btn4.setText("6 Players");
                                break;
                            case 7:
                                btn4.setText("7 Players");
                                break;
                            case 8:
                                btn4.setText("8 Players");
                                break;
                            case 9:
                                btn4.setText("9 Players");
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "MENU ERROR", Toast.LENGTH_SHORT).show();
                                break;
                        }


                        for (int i = 0; i < players.size(); i++) {
                            players.get(i).setMatches(db.query_playermatch(players.get(i).getName(), item.getItemId()));
                            players.get(i).setMatches_team(db.query_playermatch_teams(players.get(i).getName(), item.getItemId()));
                        }

                        initial_calcs();
                        players = victories_sort(players);
                        categories_calc();
                        scores_calc(mode);


                        if (listView.getAdapter().getClass().getSimpleName().equals(players_adapter.getClass().getSimpleName())) {
                            players_adapter.setPlayers(players);
                            players_adapter.notifyDataSetChanged();
                        }
                        else if (listView.getAdapter().getClass().getSimpleName().equals( categories_adapter.getClass().getSimpleName() )){
                            categories_adapter.setPlayer(plays);
                            categories_adapter.setScore(scores);
                            categories_adapter.setDates(dates);
                            for(int i=0; i<categories.size();i++){
                            Log.i("DEBUG", categories.get(i)+": "+plays.get(i)+" con "+scores.get(i)+" il "+dates.get(i));
                            }
                            categories_adapter.notifyDataSetChanged();
                        }
                        else if (listView.getAdapter().getClass().getSimpleName().equals( scores_adapter.getClass().getSimpleName() )){
                            scores_adapter.setPlayer(pl);
                            scores_adapter.setScore(sco);
                            scores_adapter.setDates(dat);
                            scores_adapter.notifyDataSetChanged();
                        }
                        else Log.i("DEBUG", "ERRORE Refresh Adapter");

                        return true;
                    }
                });

                popup.show();
            }
        });

    }


    private void initial_calcs(){

        for (int i=0;i<players.size();i++){

            int max = 0;
            int maxdate = 0;
            int average = 0;
            int low = 1000;
            int lowdate = 0;

            players.get(i).setWins(0);
            players.get(i).setSecond(0);
            players.get(i).setThird(0);

            /* Conteggio Vittorie */
            for(int j=0;j<players.get(i).getMatches().size();j++){

                switch (players.get(i).getMatches().get(j).getStanding()){
                    case 1: players.get(i).setWins(players.get(i).getWins()+1);
                        break;
                    case 2: players.get(i).setSecond(players.get(i).getSecond() + 1);
                        break;
                    case 3: players.get(i).setThird(players.get(i).getThird() + 1);
                        break;
                    default:
                        break;
                }

                /* minimo */
                if (players.get(i).getMatches().get(j).getTOT() < low) {
                    low = players.get(i).getMatches().get(j).getTOT();
                    lowdate = players.get(i).getMatches().get(j).getDate();
                }
                else if(players.get(i).getMatches().get(j).getTOT() == low &&
                        (players.get(i).getMatches().get(j).getDate() < lowdate || lowdate == 0)){
                    low = players.get(i).getMatches().get(j).getTOT();
                    lowdate = players.get(i).getMatches().get(j).getDate();
                }

                /* massimo */
                if (players.get(i).getMatches().get(j).getTOT() > max) {
                    max = players.get(i).getMatches().get(j).getTOT();
                    maxdate = players.get(i).getMatches().get(j).getDate();
                }
                else if(players.get(i).getMatches().get(j).getTOT() == max &&
                        (players.get(i).getMatches().get(j).getDate() < maxdate || maxdate == 0)){
                    max = players.get(i).getMatches().get(j).getTOT();
                    maxdate = players.get(i).getMatches().get(j).getDate();
                }

                /* media */
                average = average + players.get(i).getMatches().get(j).getTOT();
            }

            /* settaggio valori */
            players.get(i).setMax(max);
            players.get(i).setMax_date(maxdate);
            players.get(i).setLow(low);
            players.get(i).setLow_date(lowdate);

            if (players.get(i).getMatches().size() == 0){
                players.get(i).setAverage(0);
            }
            else{
                players.get(i).setAverage(average/players.get(i).getMatches().size());
            }

        }

    }


    private ArrayList<Profile> victories_sort(ArrayList<Profile> players){
//        for (int i=0; i<players.size();i++){
//            Log.i("DEBUG", players.get(i).getWins()+"F - "+players.get(i).getSecond()+"S - "+players.get(i).getThird()+"T ::: "+players.get(i).getName());
//        }
        ArrayList<Profile> sorted = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList<>();

        index.add(0);
        /* per ogni giocatore */
        for (int i=1; i<players.size(); i++){

            boolean last = true;

            /* confronta con ogni altro giocatore già controllato */
            for (int j=0; j<index.size(); j++){

                /* se ha più vittorie */
                if (players.get(i).getWins() > players.get( index.get(j) ).getWins()){
                    index.add( j,i );
                    last = false;
                    break;
                }

                /* secondi posti */
                else if (players.get(i).getWins() == players.get( index.get(j) ).getWins()){
                    if (players.get(i).getSecond() > players.get( index.get(j) ).getSecond()){
                        index.add( j,i );
                        last = false;
                        break;
                    }

                    /* Terzi posti */
                    else if (players.get(i).getSecond() == players.get( index.get(j) ).getSecond()){
                        if (players.get(i).getThird() > players.get( index.get(j) ).getThird()){
                            index.add( j,i );
                            last = false;
                            break;
                        }
                    }
                }
            }

            /* Inserimento in coda */
            if(last){
                index.add( i );
            }
        }

        for (int i=0; i<index.size(); i++){
            sorted.add( players.get( index.get(i) ) );
        }
//        Log.i("DEBUG", "---------------------------");
//
//        for (int i=0; i<sorted.size();i++){
//            Log.i("DEBUG", sorted.get(i).getWins() + "F - " + sorted.get(i).getSecond() + "S - " + sorted.get(i).getThird() + "T ::: " + sorted.get(i).getName());
//        }
        return sorted;

    }


    private void categories_init(){
        categories.add("war");
        categories.add("money");
        categories.add("wonder");
        categories.add("civil");
        categories.add("market");
        categories.add("science");
        categories.add("guilds");
        categories.add("black");
        categories.add("leaders");
        categories.add("sailors");


        plays = new ArrayList<>();
        for (int i = 0; i<categories.size();i++) plays.add("");

        dates = new ArrayList<>();
        for (int i = 0; i<categories.size();i++) scores.add(0);

        dates = new ArrayList<>();
        for (int i = 0; i<categories.size();i++) dates.add(0);

    }


    private void categories_calc(){

        Log.i("DEBUG", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Log.i("DEBUG", "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        /* Pulisci strutture dati */
        for (int i=0; i<categories.size(); i++){
            plays.set(i,"");
            scores.set(i,0);
            dates.set(i,0);
        }

        /* per ogni giocatore */
        for (int i=0; i<players.size(); i++){

            /* controlla tutti matches che ha giocato */
            for(int j=0;j<players.get(i).getMatches().size();j++) {

                if (players.get(i).getMatches().get(j).getGuerra() > 0) {
                    if (players.get(i).getMatches().get(j).getGuerra() == scores.get(0) &&
                            (players.get(i).getMatches().get(j).getDate() < dates.get(0) || dates.get(0) == 0)) {
                        dates.set(0, players.get(i).getMatches().get(j).getDate());
                        scores.set(0, players.get(i).getMatches().get(j).getGuerra());
                        plays.set(0, players.get(i).getName());
                    } else if (players.get(i).getMatches().get(j).getGuerra() > scores.get(0)) {
                        dates.set(0, players.get(i).getMatches().get(j).getDate());
                        scores.set(0, players.get(i).getMatches().get(j).getGuerra());
                        plays.set(0, players.get(i).getName());
                    }
                }

                if (players.get(i).getMatches().get(j).getMonete() > 0) {
                    if (players.get(i).getMatches().get(j).getMonete() == scores.get(1) &&
                            (players.get(i).getMatches().get(j).getDate() > dates.get(1) || dates.get(1) == 0)) {
                        dates.set(1, players.get(i).getMatches().get(j).getDate());
                        scores.set(1, players.get(i).getMatches().get(j).getMonete());
                        plays.set(1, players.get(i).getName());
                    } else if (players.get(i).getMatches().get(j).getMonete() > scores.get(1)) {
                        dates.set(1, players.get(i).getMatches().get(j).getDate());
                        scores.set(1, players.get(i).getMatches().get(j).getMonete());
                        plays.set(1, players.get(i).getName());
                    }
                }

                if (players.get(i).getMatches().get(j).getMeraviglia() > 0) {
                    if (players.get(i).getMatches().get(j).getMeraviglia() == scores.get(2) &&
                            (players.get(i).getMatches().get(j).getDate() > dates.get(2) || dates.get(2) == 0)) {
                        dates.set(2, players.get(i).getMatches().get(j).getDate());
                        scores.set(2, players.get(i).getMatches().get(j).getMeraviglia());
                        plays.set(2, players.get(i).getName());
                    } else if (players.get(i).getMatches().get(j).getMeraviglia() > scores.get(2)) {
                        dates.set(2, players.get(i).getMatches().get(j).getDate());
                        scores.set(2, players.get(i).getMatches().get(j).getMeraviglia());
                        plays.set(2, players.get(i).getName());
                    }
                }

                if (players.get(i).getMatches().get(j).getCivilta() > 0) {
                    if (players.get(i).getMatches().get(j).getCivilta() == scores.get(3) &&
                            ( players.get(i).getMatches().get(j).getDate() > dates.get(3) || dates.get(3) == 0)) {
                        dates.set(3, players.get(i).getMatches().get(j).getDate());
                        scores.set(3, players.get(i).getMatches().get(j).getCivilta());
                        plays.set(3, players.get(i).getName());
                    } else if (players.get(i).getMatches().get(j).getCivilta() > scores.get(3)) {
                        dates.set(3, players.get(i).getMatches().get(j).getDate());
                        scores.set(3, players.get(i).getMatches().get(j).getCivilta());
                        plays.set(3, players.get(i).getName());
                    }
                }

                if (players.get(i).getMatches().get(j).getMercato() > 0) {
                    if (players.get(i).getMatches().get(j).getMercato() == scores.get(4) &&
                            ( players.get(i).getMatches().get(j).getDate() > dates.get(4) || dates.get(4) == 0)) {
                        dates.set(4, players.get(i).getMatches().get(j).getDate());
                        scores.set(4, players.get(i).getMatches().get(j).getMercato());
                        plays.set(4, players.get(i).getName());
                    } else if (players.get(i).getMatches().get(j).getMercato() > scores.get(4)) {
                        dates.set(4, players.get(i).getMatches().get(j).getDate());
                        scores.set(4, players.get(i).getMatches().get(j).getMercato());
                        plays.set(4, players.get(i).getName());
                    }
                }

                if (players.get(i).getMatches().get(j).getScienza() > 0) {
                    if (players.get(i).getMatches().get(j).getScienza() == scores.get(5) &&
                            ( players.get(i).getMatches().get(j).getDate() > dates.get(5) || dates.get(5) == 0)) {
                        dates.set(5, players.get(i).getMatches().get(j).getDate());
                        scores.set(5, players.get(i).getMatches().get(j).getScienza());
                        plays.set(5, players.get(i).getName());
                    } else if (players.get(i).getMatches().get(j).getScienza() > scores.get(5)) {
                        dates.set(5, players.get(i).getMatches().get(j).getDate());
                        scores.set(5, players.get(i).getMatches().get(j).getScienza());
                        plays.set(5, players.get(i).getName());
                    }
                }

                if (players.get(i).getMatches().get(j).getGilde() > 0) {
                    if (players.get(i).getMatches().get(j).getGilde() == scores.get(6) &&
                            ( players.get(i).getMatches().get(j).getDate() > dates.get(6) || dates.get(6) == 0)) {
                        dates.set(6, players.get(i).getMatches().get(j).getDate());
                        scores.set(6, players.get(i).getMatches().get(j).getGilde());
                        plays.set(6, players.get(i).getName());
                    } else if (players.get(i).getMatches().get(j).getGilde() > scores.get(6)) {
                        dates.set(6, players.get(i).getMatches().get(j).getDate());
                        scores.set(6, players.get(i).getMatches().get(j).getGilde());
                        plays.set(6, players.get(i).getName());
                    }
                }

                if (players.get(i).getMatches().get(j).getNero() > 0) {
                    if (players.get(i).getMatches().get(j).getNero() == scores.get(7) &&
                            ( players.get(i).getMatches().get(j).getDate() > dates.get(7) || dates.get(7) == 0)) {
                        dates.set(7, players.get(i).getMatches().get(j).getDate());
                        scores.set(7, players.get(i).getMatches().get(j).getNero());
                        plays.set(7, players.get(i).getName());
                    } else if (players.get(i).getMatches().get(j).getNero() > scores.get(7)) {
                        dates.set(7, players.get(i).getMatches().get(j).getDate());
                        scores.set(7, players.get(i).getMatches().get(j).getNero());
                        plays.set(7, players.get(i).getName());
                    }
                }

                if (players.get(i).getMatches().get(j).getLeaders() > 0) {
                    if (players.get(i).getMatches().get(j).getLeaders() == scores.get(8) &&
                            ( players.get(i).getMatches().get(j).getDate() > dates.get(8) || dates.get(8) == 0)) {
                        dates.set(8, players.get(i).getMatches().get(j).getDate());
                        scores.set(8, players.get(i).getMatches().get(j).getLeaders());
                        plays.set(8, players.get(i).getName());
                    } else if (players.get(i).getMatches().get(j).getLeaders() > scores.get(8)) {
                        dates.set(8, players.get(i).getMatches().get(j).getDate());
                        scores.set(8, players.get(i).getMatches().get(j).getLeaders());
                        plays.set(8, players.get(i).getName());
                    }
                }

                if (players.get(i).getMatches().get(j).getSailors() > 0) {
                    if (players.get(i).getMatches().get(j).getSailors() == scores.get(9) &&
                            ( players.get(i).getMatches().get(j).getDate() > dates.get(9) || dates.get(9) == 0)) {
                        dates.set(9, players.get(i).getMatches().get(j).getDate());
                        scores.set(9, players.get(i).getMatches().get(j).getSailors());
                        plays.set(9, players.get(i).getName());
                    } else if (players.get(i).getMatches().get(j).getSailors() > scores.get(9)) {
                        dates.set(9, players.get(i).getMatches().get(j).getDate());
                        scores.set(9, players.get(i).getMatches().get(j).getSailors());
                        plays.set(9, players.get(i).getName());
                    }
                }
            }
        }

//        for(int i=0; i<categories.size();i++){
//            Log.i("DEBUG", categories.get(i)+": "+plays.get(i)+" con "+scores.get(i));
//        }

    }


    private void scores_calc(int mode) {

        pl = new ArrayList<>();
        sco = new ArrayList<>();
        dat = new ArrayList<>();

        ArrayList<Integer> sort = new ArrayList<>();

        if (mode == MAX) {

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getMax() != 0) {
                    sort.add(i);
                    break;
                }
            }

            if (!sort.isEmpty()) {

                for (int i = 1; i < players.size(); i++) {

                    boolean last = true;

                    for (int j = 0; j < sort.size(); j++) {

                        if (players.get(i).getMax() > players.get(sort.get(j)).getMax()) {
                            sort.add(j, i);
                            last = false;
                            break;
                        } else if (players.get(i).getMax() == players.get(sort.get(j)).getMax() && players.get(i).getMax_date() < players.get(sort.get(j)).getMax_date()) {
                            sort.add(j, i);
                            last = false;
                            break;
                        }
                    }

                    if (last && players.get(i).getMax() != 0) sort.add(i);
                }

                for (int i = 0; i < sort.size(); i++) {
                    pl.add(players.get(sort.get(i)).getName());
                    sco.add(players.get(sort.get(i)).getMax());
                    dat.add(players.get(sort.get(i)).getMax_date());
                }

            }
        }

        if (mode == MIN) {

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getLow() != 0) {
                    sort.add(i);
                    break;
                }
            }

            if (!sort.isEmpty()) {

                for (int i = 1; i < players.size(); i++) {

                    boolean last = true;

                    for (int j = 0; j < sort.size(); j++) {

                        if (players.get(i).getLow() < players.get(sort.get(j)).getLow()) {
                            sort.add(j, i);
                            last = false;
                            break;
                        } else if (players.get(i).getLow() == players.get(sort.get(j)).getLow() && players.get(i).getLow_date() < players.get(sort.get(j)).getLow_date()) {
                            sort.add(j, i);
                            last = false;
                            break;
                        }
                    }

                    if (last && players.get(i).getLow() != 1000) sort.add(i);
                }

                for (int i = 0; i < sort.size(); i++) {
                    pl.add(players.get(sort.get(i)).getName());
                    sco.add(players.get(sort.get(i)).getLow());
                    dat.add(players.get(sort.get(i)).getLow_date());
                }

            }
        }

        if (mode == AVG) {

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getAverage() != 0) {
                    sort.add(i);
                    break;
                }
            }

            if (!sort.isEmpty()) {

                for (int i = 1; i < players.size(); i++) {

                    boolean last = true;

                    for (int j = 0; j < sort.size(); j++) {

                        if (players.get(i).getAverage() > players.get(sort.get(j)).getAverage()) {
                            sort.add(j, i);
                            last = false;
                            break;
                        }
                    }

                    if (last && players.get(i).getAverage() != 0) sort.add(i);
                }

                for (int i = 0; i < sort.size(); i++) {
                    pl.add(players.get(sort.get(i)).getName());
                    sco.add(players.get(sort.get(i)).getAverage());
                    dat.add(players.get(sort.get(i)).getMatches().size());
                }

            }
        }
    }

}
