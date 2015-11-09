package com.example.pandamonium.sevenw.Storico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pandamonium.sevenw.R;
import com.example.pandamonium.sevenw.Support.Database.DBManager;
import com.example.pandamonium.sevenw.Support.Match;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pandamonium on 22/10/2015.
 */
public class Display_Match extends AppCompatActivity {

    private DBManager db;
    private Match match;
    private Display_Match_Adapter adapter;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.history_display_match);

        Intent intent = getIntent();

        int date = intent.getIntExtra("date", 0);
        int number = intent.getIntExtra("number",0);
        boolean teams = intent.getBooleanExtra("teams",false);

        db = new DBManager(this);
        if (teams) match = db.query_match_teams(date, number);
        else match = db.query_match(date, number);

        adapter = new Display_Match_Adapter(this,match.getPlayers(),teams);
        listview = (ListView) findViewById(R.id.history_match_score);
        listview.setAdapter(adapter);

    }

}
