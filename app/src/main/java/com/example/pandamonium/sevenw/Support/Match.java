package com.example.pandamonium.sevenw.Support;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Pandamonium on 18/10/2015.
 */

public class Match {

    //Per Database
    private int number;


    //Data del Match - YYYYMMDD
    private int date;
    private boolean teams;
    private ArrayList<Player> players;

    public Match(){

        number = 0;

        date = 0;
        teams = false;
        players = new ArrayList();

    }

    public int getDate(){ return date; }
    public int getNumber(){ return number; }
    public boolean getTeams(){ return teams; }
    public ArrayList<Player> getPlayers(){ return players; }

    public void setDate(int date){ this.date=date; }
    public void setNumber(int number){ this.number=number; }
    public void setTeams(boolean teams){ this.teams=teams; }
    public void setPlayers(ArrayList<Player> players){ this.players=players; }

    public void addPlayer (Player player){
        players.add(player);
    }

}

