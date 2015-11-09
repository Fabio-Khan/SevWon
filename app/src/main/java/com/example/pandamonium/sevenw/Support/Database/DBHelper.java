package com.example.pandamonium.sevenw.Support.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Pandamonium on 12/10/2015.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "SevnWonders.db";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }


    //Ricorda di disinstallare l'app dal telefono quando devi aggiornare il DB
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Lo standard per le date è YYYYMMDD
        String q = "CREATE TABLE IF NOT EXISTS " +DBStrings.TABLE+
                " ("
                +DBStrings.KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "

                +DBStrings.DATE+ " INTEGER NOT NULL, "
                +DBStrings.NUMBER+ " INTEGER DEFAULT 0, "
                +DBStrings.PARTECIPANTS+ " INTEGER NOT NULL, "

                +DBStrings.PLAYER+ " VARCHAR NOT NULL, "
                +DBStrings.POSITION+ " SMALLINT NOT NULL, "
                +DBStrings.SCORE+ " INTEGER NOT NULL, "

                +DBStrings.GUERRA+ " SMALLINT DEFAULT -1, "
                +DBStrings.MONETE+ " SMALLINT DEFAULT -1, "
                +DBStrings.MERAVIGLIA+ " SMALLINT DEFAULT -1, "
                +DBStrings.CIVILTA+ " SMALLINT DEFAULT -1, "
                +DBStrings.MERCATO+ " SMALLINT DEFAULT -1, "
                +DBStrings.SCIENZA+ " SMALLINT DEFAULT -1, "
                +DBStrings.GILDE+ " SMALLINT DEFAULT -1, "
                +DBStrings.NERO+ " SMALLINT DEFAULT -1, "
                +DBStrings.LEADERS+ " SMALLINT DEFAULT -1, "
                +DBStrings.SAILORS+ " SMALLINT DEFAULT -1" +
                ")";


        String p = "CREATE TABLE IF NOT EXISTS " +DBStrings.TABLET+
                " ("
                +DBStrings.KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "

                +DBStrings.DATE+ " INTEGER NOT NULL, "
                +DBStrings.NUMBER+ " INTEGER DEFAULT 0, "
                +DBStrings.PARTECIPANTS+ " INTEGER NOT NULL, "

                +DBStrings.PLAYER+ " VARCHAR NOT NULL, "
                +DBStrings.TEAM+ " INTEGER NOT NULL, " //teams
                +DBStrings.POSITION+ " SMALLINT NOT NULL, "
                +DBStrings.SCORE+ " INTEGER NOT NULL, "
                +DBStrings.TEAMSCORE+ " INTEGER NOT NULL, " //teams

                +DBStrings.GUERRA+ " SMALLINT DEFAULT -1, "
                +DBStrings.MONETE+ " SMALLINT DEFAULT -1, "
                +DBStrings.MERAVIGLIA+ " SMALLINT DEFAULT -1, "
                +DBStrings.CIVILTA+ " SMALLINT DEFAULT -1, "
                +DBStrings.MERCATO+ " SMALLINT DEFAULT -1, "
                +DBStrings.SCIENZA+ " SMALLINT DEFAULT -1, "
                +DBStrings.GILDE+ " SMALLINT DEFAULT -1, "
                +DBStrings.NERO+ " SMALLINT DEFAULT -1, "
                +DBStrings.LEADERS+ " SMALLINT DEFAULT -1, "
                +DBStrings.SAILORS+ " SMALLINT DEFAULT -1" +
                ")";


        String u = "CREATE TABLE IF NOT EXISTS " +DBStrings.PTABLE+
                " ("
                +DBStrings.KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                +DBStrings.DATE+ " INTEGER NOT NULL, "
                +DBStrings.PLAYER+ " VARCHAR NOT NULL" +
                ")";


        //gli scores sono con DEFAULT -1 per indicare che non sono state selezionate (no exps)
        Log.i("QUERY", q);
        Log.i("QUERY", p);
        Log.i("QUERY", u);

        db.execSQL(q);
        db.execSQL(p);
        db.execSQL(u);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + DBStrings.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBStrings.TABLET);
        db.execSQL("DROP TABLE IF EXISTS " + DBStrings.PTABLE);

        // Create tables again
        onCreate(db);
    }

}
