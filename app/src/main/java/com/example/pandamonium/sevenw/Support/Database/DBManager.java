package com.example.pandamonium.sevenw.Support.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.pandamonium.sevenw.Support.Match;
import com.example.pandamonium.sevenw.Support.Player;
import com.example.pandamonium.sevenw.Support.Profile;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 12/10/2015.
 */
public class DBManager {

    private DBHelper dbHelper;
    private Context appcontext;

    public DBManager(Context context){
        dbHelper = new DBHelper(context);
        appcontext = context;
    }


    public void save_match (Match match){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int number = 0;
        int current;

        Cursor c = db.rawQuery("SELECT * FROM "+DBStrings.TABLE+
                " WHERE "+DBStrings.DATE+"="+match.getDate(), null);

        if(c.getCount()==0){
            number = 0;
        }
        else{
            while(c.moveToNext()){
                current = c.getInt(c.getColumnIndex(DBStrings.NUMBER));
                if (current > number) {
                    number = current;
                }
            }
        }
        number = number +1;

        for (int i = 0; i<match.getPlayers().size(); i++) {
            String q = "INSERT INTO " + DBStrings.TABLE +
                    " ("
                    + DBStrings.DATE + ","
                    + DBStrings.NUMBER + ","
                    + DBStrings.PARTECIPANTS + ","

                    + DBStrings.PLAYER + ","
                    + DBStrings.POSITION + ","
                    + DBStrings.SCORE + ","

                    + DBStrings.GUERRA + ","
                    + DBStrings.MONETE + ","
                    + DBStrings.MERAVIGLIA + ","
                    + DBStrings.CIVILTA + ","
                    + DBStrings.MERCATO + ","

                    + DBStrings.SCIENZA + ","
                    + DBStrings.GILDE + ","
                    + DBStrings.NERO + ","
                    + DBStrings.LEADERS + ","
                    + DBStrings.SAILORS +
                    ") VALUES('"
                    + match.getDate() + "','"
                    + (number) + "','"
                    + match.getPlayers().size() + "','"

                    + match.getPlayers().get(i).getName() + "','"
                    + match.getPlayers().get(i).getStanding() + "','"
                    + match.getPlayers().get(i).getTOT() + "','"

                    + match.getPlayers().get(i).getGuerra()+"','"
                    + match.getPlayers().get(i).getMonete()+"','"
                    + match.getPlayers().get(i).getMeraviglia()+"','"
                    + match.getPlayers().get(i).getCivilta()+"','"
                    + match.getPlayers().get(i).getMercato()+"','"

                    + match.getPlayers().get(i).getScienza()+"','"
                    + match.getPlayers().get(i).getGilde()+"','"
                    + match.getPlayers().get(i).getNero()+"','"
                    + match.getPlayers().get(i).getLeaders()+"','"
                    + match.getPlayers().get(i).getSailors()+"');"
                    ;

            Log.i("QUERY", q);
            db.execSQL(q);
        }

        c.close();
        db.close();
    }


    public void save_match_teams (Match match){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int number = 0;
        int current;

        Cursor c = db.rawQuery("SELECT * FROM "+DBStrings.TABLET+
                " WHERE "+DBStrings.DATE+"="+match.getDate(), null);

        if(c.getCount()==0){
            number = 0;
        }
        else{
            while(c.moveToNext()){
                current = c.getInt(c.getColumnIndex(DBStrings.NUMBER));
                if (current > number) {
                    number = current;
                }
            }
        }
        number = number +1;

        for (int i = 0; i<match.getPlayers().size(); i++) {
            String q = "INSERT INTO " + DBStrings.TABLET +
                    " ("
                    + DBStrings.DATE + ","
                    + DBStrings.NUMBER + ","
                    + DBStrings.PARTECIPANTS + ","

                    + DBStrings.PLAYER + ","
                    + DBStrings.TEAM + ","
                    + DBStrings.POSITION + ","
                    + DBStrings.SCORE + ","
                    + DBStrings.TEAMSCORE + ","

                    + DBStrings.GUERRA + ","
                    + DBStrings.MONETE + ","
                    + DBStrings.MERAVIGLIA + ","
                    + DBStrings.CIVILTA + ","
                    + DBStrings.MERCATO + ","

                    + DBStrings.SCIENZA + ","
                    + DBStrings.GILDE + ","
                    + DBStrings.NERO + ","
                    + DBStrings.LEADERS + ","
                    + DBStrings.SAILORS +
                    ") VALUES('"
                    + match.getDate() + "','"
                    + (number) + "','"
                    + match.getPlayers().size() + "','"

                    + match.getPlayers().get(i).getName() + "','"
                    + match.getPlayers().get(i).getTeam() + "','"
                    + match.getPlayers().get(i).getStanding() + "','"
                    + match.getPlayers().get(i).getTOT() + "','"
                    + match.getPlayers().get(i).getTeam_TOT() + "','"

                    + match.getPlayers().get(i).getGuerra()+"','"
                    + match.getPlayers().get(i).getMonete()+"','"
                    + match.getPlayers().get(i).getMeraviglia()+"','"
                    + match.getPlayers().get(i).getCivilta()+"','"
                    + match.getPlayers().get(i).getMercato()+"','"

                    + match.getPlayers().get(i).getScienza()+"','"
                    + match.getPlayers().get(i).getGilde()+"','"
                    + match.getPlayers().get(i).getNero()+"','"
                    + match.getPlayers().get(i).getLeaders()+"','"
                    + match.getPlayers().get(i).getSailors()+"');"
                    ;

            Log.i("QUERY", q);
            db.execSQL(q);
        }

        c.close();
        db.close();
    }

    public void delete_match(Match match){

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String table;

        if (match.getTeams()) table = DBStrings.TABLET;
        else table = DBStrings.TABLE;

        String q = "DELETE FROM "+table+" WHERE "
                +DBStrings.DATE+" = '"+match.getDate()+"' AND "
                +DBStrings.NUMBER+" = '"+match.getNumber()+"';";

        Log.i("QUERY",q);
        db.execSQL(q);

        Toast.makeText(appcontext, "Cancellato Match "+match.getDate()+" - "+match.getNumber()+" !", Toast.LENGTH_SHORT).show();

        db.close();
    }


    public ArrayList<Match> query_history(){

        ArrayList<Match> history = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DBStrings.TABLE, null);

        if(c.getCount()==0){
            Toast.makeText(appcontext, "No matches in History", Toast.LENGTH_SHORT).show();
        }

        else{

            Match match = new Match();
            Player play;
            boolean set = false;

            while(c.moveToNext()){

                if (set &&
                        (match.getNumber() != c.getInt(c.getColumnIndex(DBStrings.NUMBER)) ||
                        match.getDate() != c.getInt(c.getColumnIndex(DBStrings.DATE)))) {

                    debug_match(match);

                    history.add(match);
                    set = false;
                }

                if (!set){
                    match = new Match();
                    match.setDate(c.getInt(c.getColumnIndex(DBStrings.DATE)));
                    match.setNumber(c.getInt(c.getColumnIndex(DBStrings.NUMBER)));
                    set = true;
                }

                play = new Player(false,"",0);
                play.setName(c.getString(c.getColumnIndex(DBStrings.PLAYER)));
                play.setStanding(c.getInt(c.getColumnIndex(DBStrings.POSITION)));
                play.setTOT(c.getInt(c.getColumnIndex(DBStrings.SCORE)));

                play.setGuerra(c.getInt(c.getColumnIndex(DBStrings.GUERRA)));
                play.setMonete(c.getInt(c.getColumnIndex(DBStrings.MONETE)));
                play.setMeraviglia(c.getInt(c.getColumnIndex(DBStrings.MERAVIGLIA)));
                play.setCivilta(c.getInt(c.getColumnIndex(DBStrings.CIVILTA)));
                play.setMercato(c.getInt(c.getColumnIndex(DBStrings.MERCATO)));
                play.setScienza(c.getInt(c.getColumnIndex(DBStrings.SCIENZA)));
                play.setGilde(c.getInt(c.getColumnIndex(DBStrings.GILDE)));
                play.setNero(c.getInt(c.getColumnIndex(DBStrings.NERO)));
                play.setLeaders(c.getInt(c.getColumnIndex(DBStrings.LEADERS)));
                play.setSailors(c.getInt(c.getColumnIndex(DBStrings.SAILORS)));

                match.getPlayers().add(play);

                if (set && c.isLast()) {
                    history.add(match);
                    set = false;
                }
            }
        }

        c.close();
        db.close();

        return history;
    }


    public ArrayList<Match> query_history_teams(){

        ArrayList<Match> history = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DBStrings.TABLET, null);

        if(c.getCount()==0){
            Toast.makeText(appcontext, "No matches in TEAMS History", Toast.LENGTH_SHORT).show();
        }

        else{

            Match match = new Match();
            Player play;
            boolean set = false;

            while(c.moveToNext()){

                if (set &&
                        (match.getNumber() != c.getInt(c.getColumnIndex(DBStrings.NUMBER)) ||
                                match.getDate() != c.getInt(c.getColumnIndex(DBStrings.DATE)))) {

                    debug_match(match);

                    history.add(match);
                    set = false;
                }

                if (!set){
                    match = new Match();
                    match.setDate(c.getInt(c.getColumnIndex(DBStrings.DATE)));
                    match.setNumber(c.getInt(c.getColumnIndex(DBStrings.NUMBER)));
                    set = true;
                }

                play = new Player(false,"",0);
                play.setName(c.getString(c.getColumnIndex(DBStrings.PLAYER)));
                play.setTeam(c.getInt(c.getColumnIndex(DBStrings.TEAM)));
                play.setStanding(c.getInt(c.getColumnIndex(DBStrings.POSITION)));
                play.setTOT(c.getInt(c.getColumnIndex(DBStrings.SCORE)));
                play.setTeam_TOT(c.getInt(c.getColumnIndex(DBStrings.TEAMSCORE)));

                play.setGuerra(c.getInt(c.getColumnIndex(DBStrings.GUERRA)));
                play.setMonete(c.getInt(c.getColumnIndex(DBStrings.MONETE)));
                play.setMeraviglia(c.getInt(c.getColumnIndex(DBStrings.MERAVIGLIA)));
                play.setCivilta(c.getInt(c.getColumnIndex(DBStrings.CIVILTA)));
                play.setMercato(c.getInt(c.getColumnIndex(DBStrings.MERCATO)));
                play.setScienza(c.getInt(c.getColumnIndex(DBStrings.SCIENZA)));
                play.setGilde(c.getInt(c.getColumnIndex(DBStrings.GILDE)));
                play.setNero(c.getInt(c.getColumnIndex(DBStrings.NERO)));
                play.setLeaders(c.getInt(c.getColumnIndex(DBStrings.LEADERS)));
                play.setSailors(c.getInt(c.getColumnIndex(DBStrings.SAILORS)));

                match.getPlayers().add(play);

                if (set && c.isLast()) {
                    history.add(match);
                    set = false;
                }
            }
        }

        c.close();
        db.close();

        return history;
    }


    public Match query_match(int date, int number){

        Match match = new Match();
        Player play;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String q = "SELECT * FROM "+DBStrings.TABLE+" WHERE "
                    +DBStrings.DATE+" = '"+date+"' AND "
                    +DBStrings.NUMBER+" = '"+number+"';";

        Cursor c = db.rawQuery(q, null);

        if(c.getCount()==0){
            Toast.makeText(appcontext, "Match not found!", Toast.LENGTH_SHORT).show();
        }

        else {

            boolean once = false;

            while (c.moveToNext()) {

                if (!once) {
                    match.setDate(c.getInt(c.getColumnIndex(DBStrings.DATE)));
                    match.setNumber(c.getInt(c.getColumnIndex(DBStrings.NUMBER)));
                    once = true;
                }

                play = new Player(false, "", 0);
                play.setName(c.getString(c.getColumnIndex(DBStrings.PLAYER)));
                play.setStanding(c.getInt(c.getColumnIndex(DBStrings.POSITION)));
                play.setTOT(c.getInt(c.getColumnIndex(DBStrings.SCORE)));

                play.setGuerra(c.getInt(c.getColumnIndex(DBStrings.GUERRA)));
                play.setMonete(c.getInt(c.getColumnIndex(DBStrings.MONETE)));
                play.setMeraviglia(c.getInt(c.getColumnIndex(DBStrings.MERAVIGLIA)));
                play.setCivilta(c.getInt(c.getColumnIndex(DBStrings.CIVILTA)));
                play.setMercato(c.getInt(c.getColumnIndex(DBStrings.MERCATO)));
                play.setScienza(c.getInt(c.getColumnIndex(DBStrings.SCIENZA)));
                play.setGilde(c.getInt(c.getColumnIndex(DBStrings.GILDE)));
                play.setNero(c.getInt(c.getColumnIndex(DBStrings.NERO)));
                play.setLeaders(c.getInt(c.getColumnIndex(DBStrings.LEADERS)));
                play.setSailors(c.getInt(c.getColumnIndex(DBStrings.SAILORS)));

                match.getPlayers().add(play);
            }
        }

        debug_match(match);

        c.close();
        db.close();

        return match;
    }


    public Match query_match_teams(int date, int number){

        Match match = new Match();
        Player play;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String q = "SELECT * FROM "+DBStrings.TABLET+" WHERE "
                +DBStrings.DATE+" = '"+date+"' AND "
                +DBStrings.NUMBER+" = '"+number+"';";

        Cursor c = db.rawQuery(q, null);

        if(c.getCount()==0){
            Toast.makeText(appcontext, "Match not found!", Toast.LENGTH_SHORT).show();
        }

        else {

            boolean once = false;

            while (c.moveToNext()) {

                if (!once) {
                    match.setDate(c.getInt(c.getColumnIndex(DBStrings.DATE)));
                    match.setNumber(c.getInt(c.getColumnIndex(DBStrings.NUMBER)));
                    once = true;
                }

                play = new Player(false, "", 0);
                play.setName(c.getString(c.getColumnIndex(DBStrings.PLAYER)));
                play.setTeam(c.getInt(c.getColumnIndex(DBStrings.TEAM)));
                play.setStanding(c.getInt(c.getColumnIndex(DBStrings.POSITION)));
                play.setTOT(c.getInt(c.getColumnIndex(DBStrings.SCORE)));
                play.setTeam_TOT(c.getInt(c.getColumnIndex(DBStrings.TEAMSCORE)));

                play.setGuerra(c.getInt(c.getColumnIndex(DBStrings.GUERRA)));
                play.setMonete(c.getInt(c.getColumnIndex(DBStrings.MONETE)));
                play.setMeraviglia(c.getInt(c.getColumnIndex(DBStrings.MERAVIGLIA)));
                play.setCivilta(c.getInt(c.getColumnIndex(DBStrings.CIVILTA)));
                play.setMercato(c.getInt(c.getColumnIndex(DBStrings.MERCATO)));
                play.setScienza(c.getInt(c.getColumnIndex(DBStrings.SCIENZA)));
                play.setGilde(c.getInt(c.getColumnIndex(DBStrings.GILDE)));
                play.setNero(c.getInt(c.getColumnIndex(DBStrings.NERO)));
                play.setLeaders(c.getInt(c.getColumnIndex(DBStrings.LEADERS)));
                play.setSailors(c.getInt(c.getColumnIndex(DBStrings.SAILORS)));

                match.getPlayers().add(play);
            }
        }

        debug_match(match);

        c.close();
        db.close();

        return match;
    }


    public void save_player(String name, int date){

        boolean exists = false;

        //Controlla se il nome è nullo o è "Name"
        if (name.isEmpty() || name.equals("Name")){
            Toast.makeText(appcontext, "Invalid name!", Toast.LENGTH_SHORT).show();
        }
        else{
            //Query per controllare se l'inserimento è lecito
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM "+DBStrings.PTABLE, null);

            while(c.moveToNext()){
                if (name.equals(c.getString(c.getColumnIndex(DBStrings.PLAYER)))){
                    Toast.makeText(appcontext, "Player already exists!", Toast.LENGTH_SHORT).show();
                    exists = true;
                    break;
                }
            }

            if (!exists){
                String q = "INSERT INTO " + DBStrings.PTABLE +
                        " ("
                        + DBStrings.DATE + ","
                        + DBStrings.PLAYER +
                        ") VALUES('"
                        + date + "','"
                        + name + "');";

                Log.i("QUERY", q);
                db.execSQL(q);
                Toast.makeText(appcontext, "Added: "+name, Toast.LENGTH_SHORT).show();
            }

            c.close();
            db.close();

        }

    }


    public ArrayList<Profile> query_players(){

        ArrayList<Profile> players = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DBStrings.PTABLE+ " ORDER BY "+DBStrings.PLAYER, null);

        if(c.getCount()==0){
            Toast.makeText(appcontext, "No Players Registered", Toast.LENGTH_SHORT).show();
        }

        else{

            while(c.moveToNext()){

                Profile player = new Profile();

                player.setDate(c.getInt(c.getColumnIndex(DBStrings.DATE)));
                player.setName(c.getString(c.getColumnIndex(DBStrings.PLAYER)));

                players.add(player);

            }
        }

        c.close();
        db.close();

        return players;
    }


    public void delete_player(String name){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String q = "DELETE FROM "+DBStrings.PTABLE+" " +
                   "WHERE "+DBStrings.PLAYER+"='"+name+"';";

        Log.i("QUERY",q);
        db.execSQL(q);

        Toast.makeText(appcontext, "Cancellato "+name+" dal Database!", Toast.LENGTH_SHORT).show();

        db.close();
    }


    public ArrayList<Player> query_playermatch(String name, int mode){

        ArrayList<Player> plhistory = new ArrayList<>();
        Player play;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String q = "";

        if(mode == 1){
            q = "SELECT * FROM "+DBStrings.TABLE+" WHERE "
                    +DBStrings.PLAYER+" = '"+name+"';";
        }
        else if (2 <= mode && mode <= 9){
            q = "SELECT * FROM "+DBStrings.TABLE+" WHERE "
                    +DBStrings.PLAYER+" = '"+name+"' AND "
                    +DBStrings.PARTECIPANTS+" = '"+mode+"';";
        }
        else Log.i("QUERY", "Errore Modo query_playermatch");

        Log.i("QUERY", q);
        Cursor c = db.rawQuery(q, null);

        if(c.getCount()==0){
            //nothing
        }

        else {

            while (c.moveToNext()) {

                play = new Player(false, "", 0);
                play.setName(c.getString(c.getColumnIndex(DBStrings.PLAYER)));
                play.setStanding(c.getInt(c.getColumnIndex(DBStrings.POSITION)));
                play.setTOT(c.getInt(c.getColumnIndex(DBStrings.SCORE)));
                play.setDate(c.getInt(c.getColumnIndex(DBStrings.DATE)));
                play.setNum(c.getInt(c.getColumnIndex(DBStrings.NUMBER)));
                play.setGiocs(c.getInt(c.getColumnIndex(DBStrings.PARTECIPANTS)));

                play.setGuerra(c.getInt(c.getColumnIndex(DBStrings.GUERRA)));
                play.setMonete(c.getInt(c.getColumnIndex(DBStrings.MONETE)));
                play.setMeraviglia(c.getInt(c.getColumnIndex(DBStrings.MERAVIGLIA)));
                play.setCivilta(c.getInt(c.getColumnIndex(DBStrings.CIVILTA)));
                play.setMercato(c.getInt(c.getColumnIndex(DBStrings.MERCATO)));
                play.setScienza(c.getInt(c.getColumnIndex(DBStrings.SCIENZA)));
                play.setGilde(c.getInt(c.getColumnIndex(DBStrings.GILDE)));
                play.setNero(c.getInt(c.getColumnIndex(DBStrings.NERO)));
                play.setLeaders(c.getInt(c.getColumnIndex(DBStrings.LEADERS)));
                play.setSailors(c.getInt(c.getColumnIndex(DBStrings.SAILORS)));

                plhistory.add(play);
            }

        }

        c.close();
        db.close();

//        for (int i=0;i<plhistory.size();i++) {
//            Log.i("DEBUG", plhistory.get(i).getName() + ": " +
//                    + plhistory.get(i).getDate() + " - " + plhistory.get(i).getNum() + " - "
//                    + plhistory.get(i).getTOT() + " - " + plhistory.get(i).getStanding());
//        }

        return plhistory;
    }


    public ArrayList<Player> query_playermatch_teams(String name, int mode){

        ArrayList<Player> matches = new ArrayList<>();



        return matches;
    }


    private void debug_query(){

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+DBStrings.TABLE, null);

        if(c.getCount()==0){
            Toast.makeText(appcontext, "No matches found", Toast.LENGTH_SHORT).show();
        }

        else{
            while(c.moveToNext()){
                Log.i("DATABASE: ",""+c.getInt(c.getColumnIndex(DBStrings.DATE))+" "
                        +c.getInt(c.getColumnIndex(DBStrings.NUMBER))+" "
                +c.getString(c.getColumnIndex(DBStrings.PLAYER))+" "
                +c.getInt(c.getColumnIndex(DBStrings.POSITION))+" "
                +c.getInt(c.getColumnIndex(DBStrings.SCORE)));
            }

        }

        c.close();
        db.close();
    }



    private void debug_match(Match match){
        Log.i("DATABASE", "MATCH: " + match.getDate() + " - " + match.getNumber() + " - " + match.getPlayers().size());
        for (int i = 0; i<match.getPlayers().size(); i++){
            Log.i("DATABASE", "" + match.getPlayers().get(i).getName() +
                    " - " + match.getPlayers().get(i).getStanding() +
                    " - " + match.getPlayers().get(i).getTOT()+ "\n" +
                    +match.getPlayers().get(i).getGuerra() + " - "
                    +match.getPlayers().get(i).getMonete() + " - "
                    +match.getPlayers().get(i).getMeraviglia() + " - "
                    +match.getPlayers().get(i).getCivilta() + " - "
                    +match.getPlayers().get(i).getMercato() + " - "
                    +match.getPlayers().get(i).getGilde() + " - "
                    +match.getPlayers().get(i).getScienza() + " - "
                    +match.getPlayers().get(i).getNero() + " - "
                    +match.getPlayers().get(i).getLeaders() + " - "
                    +match.getPlayers().get(i).getSailors());
        }
    }
}
