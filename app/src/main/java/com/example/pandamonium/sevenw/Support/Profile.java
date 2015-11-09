package com.example.pandamonium.sevenw.Support;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 05/11/2015.
 */
public class Profile {

    /* Player */
    private String name;
    private int date;

    /* Player History */
    private ArrayList<Player> matches = new ArrayList<>();
    private ArrayList<Player> matches_team = new ArrayList<>();

    /* Player Statistics */
    private int victory_standing;
    private int wins;
    private int second;
    private int third;

    /* Scores */
    private int max;
    private int average;
    private int low;
    private int max_date;
    private int low_date;

    public Profile(){
        this.name="";
        this.date=0;

        this.victory_standing = 0;
        this.wins = 0;
        this.second = 0;
        this.third = 0;

        this.max = 0;
        this.average = 0;
        this.low = 0;

        this.max_date = 0;
        this.low_date = 0;
    }

    public Profile(String name, int date){
        this.name=name;
        this.date=date;
    }

    public String getName(){ return this.name; }
    public int getDate(){ return this.date; }
    public ArrayList<Player> getMatches(){ return this.matches; }
    public ArrayList<Player> getMatches_team(){ return this.matches_team; }

    public void setName(String name){ this.name=name; }
    public void setDate(int date){ this.date=date; }
    public void setMatches(ArrayList<Player> matches){ this.matches=matches; }
    public void setMatches_team(ArrayList<Player> matches){ this.matches_team=matches; }

    public int getVictory_standing() {
        return victory_standing;
    }
    public void setVictory_standing(int victory_standing) {
        this.victory_standing = victory_standing;
    }

    public int getWins() {
        return wins;
    }
    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getSecond() {
        return second;
    }
    public void setSecond(int second) {
        this.second = second;
    }

    public int getThird() {
        return third;
    }
    public void setThird(int third) {
        this.third = third;
    }

    public int getMax() {
        return max;
    }
    public void setMax(int max) {
        this.max = max;
    }

    public int getAverage() {
        return average;
    }
    public void setAverage(int average) {
        this.average = average;
    }

    public int getLow() {
        return low;
    }
    public void setLow(int low) {
        this.low = low;
    }

    public int getMax_date() {
        return max_date;
    }

    public void setMax_date(int max_date) {
        this.max_date = max_date;
    }

    public int getLow_date() {
        return low_date;
    }

    public void setLow_date(int low_date) {
        this.low_date = low_date;
    }


}
