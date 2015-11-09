package com.example.pandamonium.sevenw.Players;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pandamonium.sevenw.New_Game.Expansions_Selection.ExpansionSelection;
import com.example.pandamonium.sevenw.New_Game.Game_Match.Match_Row_Adapter_Teams;
import com.example.pandamonium.sevenw.R;
import com.example.pandamonium.sevenw.Support.Database.DBManager;
import com.example.pandamonium.sevenw.Support.Player;
import com.example.pandamonium.sevenw.Support.Profile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Pandamonium on 14/10/2015.
 */
public class Main_Players extends AppCompatActivity {

    private ArrayList<Profile> players = new ArrayList<>();

    DBManager db;
    private ListView listView;
    private Players_adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.players_main);

        db = new DBManager(this);
        players = db.query_players();

        adapter = new Players_adapter(this, players,db);
        listView = (ListView) findViewById(R.id.delplayers);
        listView.setAdapter(adapter);

        ImageButton btn = (ImageButton)findViewById(R.id.newplayer_btn);
        final EditText edtxt = (EditText)findViewById(R.id.insertplayer);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){

                db.save_player(edtxt.getText().toString(),get_date());
                players = db.query_players();
                adapter.setPlayers(players);
                adapter.notifyDataSetChanged();

            }
        });

    }

    public Integer get_date(){

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = df.format(calendar.getTime());
        Integer data = Integer.parseInt(formattedDate);

        return data;

    }

}
