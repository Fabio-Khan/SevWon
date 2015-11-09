package com.example.pandamonium.sevenw.New_Game.Game_Match;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.pandamonium.sevenw.R;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 08/10/2015.
 */

public class Match_Score extends AppCompatActivity {

    private TextView textview;
    private GridView grid;

    private ArrayList<String> exps = new ArrayList<>();
    private String player = new String();

    //Elementi di Ritorno
    private ArrayList<String> points = new ArrayList<>();
    private ArrayList<Integer> scores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.game_scoring);

        Intent intent = getIntent();
        exps = intent.getStringArrayListExtra("expansions");
        player = intent.getStringExtra("player");

        textview = (TextView) findViewById(R.id.playerscore);
        textview.setText(player);

        points = expansion_context();

        grid = (GridView)findViewById(R.id.score_grid);
        grid.setAdapter(new Match_Score_Adapter(this, points, scores));

        Button done = (Button)findViewById(R.id.score_done);

        /* Quando è premuto il pulsante, torna il punteggio alla schermata dei punteggi */
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent resultData = new Intent();
                resultData.putIntegerArrayListExtra("PUNTEGGIO", scores);
                setResult(Activity.RESULT_OK, resultData);
                finish();

            }
        });
    }


    private ArrayList<String> expansion_context(){

        ArrayList<String> points = new ArrayList<>();

        points.add("guerra");
        scores.add(0);
        points.add("monete");
        scores.add(0);
        points.add("meraviglia");
        scores.add(0);
        points.add("civilta");
        scores.add(0);
        points.add("mercato");
        scores.add(0);
        points.add("scienza");
        scores.add(0);
        points.add("gilde");
        scores.add(0);

        if (exps.contains("Cities")) {
            points.add("nero");
            scores.add(0);
        }
        if (exps.contains("Leaders")) {
            points.add("exp_leaders");
            scores.add(0);
        }
        if (exps.contains("Sailors")) {
            points.add("exp_sailors");
            scores.add(0);
        }

        return points;
    }

}
