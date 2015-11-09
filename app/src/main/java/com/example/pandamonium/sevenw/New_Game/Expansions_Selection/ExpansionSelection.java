package com.example.pandamonium.sevenw.New_Game.Expansions_Selection;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.example.pandamonium.sevenw.New_Game.Players_Selection.ChosePlayers;
import com.example.pandamonium.sevenw.Support.Expansion;
import com.example.pandamonium.sevenw.R;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 17/09/2015.
 */

public class ExpansionSelection extends AppCompatActivity {

    //numero di espansioni previste.
    private final int numexp = 3;

    //array di espansioni disponibili.
    private ArrayList<Expansion> exps = new ArrayList<>();

    // Supporto all'activity.
    private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_expsel);

        build_list();

        gridView = (GridView) findViewById(R.id.exp_list);
        gridView.setAdapter(new ListAdapter(this, exps));

    }


    /* Gestione della Action Bar */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next_btn, menu);
        return true;
    }

    /* Quando viene premuto un pulsante della Action Bar */
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //sono passate le espansioni selezionate  e il num di giocatori max
        switch(id){
            case R.id.next_btn:
                Intent intent = new Intent (this, ChosePlayers.class);
                intent.putStringArrayListExtra("expansions", Chosen_Exp());
                intent.putExtra("maxP", maxP());
                startActivity(intent);
                return true;
            default:
                Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }


    /* Qui sono definite tutte le espansioni IN ORDINE */
    private void build_list() {

        for (int i=0; i<numexp; i++){
            switch (i){

                case 0:
                    exps.add(new Expansion(false,
                            "Cities",
                            R.drawable.exp_cities));
                    break;

                case 1:
                    exps.add(new Expansion(false,
                            "Leaders",
                            R.drawable.exp_leaders));
                    break;

                case 2:
                    exps.add(new Expansion(false,
                            "Sailors",
                            R.drawable.exp_sailors));
                    break;

                case 3:
                    exps.add(new Expansion(false,
                            "Myths",
                            R.drawable.exp_myths));
                    break;

                case 4:
                    exps.add(new Expansion(false,
                            "Empires",
                            R.drawable.exp_empires));
                    break;

                case 5:
                    exps.add(new Expansion(false,
                            "Ruins",
                            R.drawable.exp_ruins));
                    break;

                case 6:
                    exps.add(new Expansion(false,
                            "Lost Wonders",
                            R.drawable.exp_lostwonders));
                    break;

                case 7:
                    exps.add(new Expansion(false,
                            "Game Wonders",
                            R.drawable.exp_gamewonders));
                    break;

                case 8:
                    exps.add(new Expansion(false,
                            "Collection",
                            R.drawable.exp_collection));
                    break;

                default:
                    exps.add(new Expansion(false,
                            "ERROR!!",
                            R.mipmap.ic_launcher));
                    break;
            }
        }

    }


    /**
     * Dato l'insieme di tutte le espansioni, ritorna una stringa con i nomi
     * soltanto di quelle scelte.
     */
    private ArrayList<String> Chosen_Exp(){

        ArrayList<String> game = new ArrayList<>();

        for (int i=0; i<numexp; i++){
            if (exps.get(i).isSelected()){
                game.add(exps.get(i).getName());
            }
        }

        return game;
    }


    /**
     * Ritorna il numero massimo di giocatori a seconda delle espansioni scelte
     */
    private int maxP(){

        int max = 7;
        ArrayList<String> game = Chosen_Exp();

        /*
         - Cities: +1 player. Punteggi Neri.
         - Leaders: punteggi bianchi.
         - Sailors: Punteggi Arancioni.+1 Player.
         */

        for (int i=0; i<game.size(); i++){
            if (game.get(i) == "Cities") max++;
            if (game.get(i) == "Sailors") max++;
        }

        return max;
    }

}
