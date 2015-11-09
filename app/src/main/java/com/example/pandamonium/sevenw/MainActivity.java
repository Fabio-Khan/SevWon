package com.example.pandamonium.sevenw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pandamonium.sevenw.New_Game.Expansions_Selection.ExpansionSelection;
import com.example.pandamonium.sevenw.Players.Main_Players;
import com.example.pandamonium.sevenw.Statistics.Main_Statistics;
import com.example.pandamonium.sevenw.Storico.StoricoPartite;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.main_game);

        // Riferimenti ai bottoni
        Button btn1 = (Button)findViewById(R.id.button1);
        Button btn2 = (Button)findViewById(R.id.button2);
        Button btn3 = (Button)findViewById(R.id.button3);
        Button btn4 = (Button)findViewById(R.id.button4);

        // Configuro Handlers Eventi
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                /*  definisco l'intenzione  */
                Intent openNewGame = new Intent(MainActivity.this,ExpansionSelection.class);
                /* passo all'attivazione dell'activity Expansion Selector */
                startActivity(openNewGame);
            }
        });


        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                Intent openPlayers = new Intent(MainActivity.this,Main_Players.class);
                startActivity(openPlayers);
            }
        });


        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                Intent openStatistics = new Intent(MainActivity.this,Main_Statistics.class);
                startActivity(openStatistics);
            }
        });


        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                Intent openHistory = new Intent(MainActivity.this,StoricoPartite.class);
                startActivity(openHistory);
            }
        });

    }

    //TODO Aggiustare il formato di visualizzazione delle date

}
