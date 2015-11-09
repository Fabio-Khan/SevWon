package com.example.pandamonium.sevenw.New_Game.Game_Match;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandamonium.sevenw.Support.Player;
import com.example.pandamonium.sevenw.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Pandamonium on 06/10/2015.
 */

public class Match_Row_Adapter_Teams extends BaseAdapter {

    private Activity sActivity;
    private ArrayList<Player> players;
    private ArrayList<String> exps = new ArrayList<>();

    //supporto per la classifica
    private ArrayList<Integer> standings;
    public static final Integer REQUEST_TEAMS = 2;
    private int lastclicked;

    public Match_Row_Adapter_Teams(Activity sActivity, ArrayList<Player> players, ArrayList<String> exps) {
        this.sActivity = sActivity;
        this.players = players;
        this.standings = new ArrayList<>();
        this.exps = exps;
        this.lastclicked = -1;
    }


    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int position) {
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final ViewHolder holder;

        if(view == null) {

            LayoutInflater li = sActivity.getLayoutInflater();
            view = li.inflate(R.layout.game_match_teams_row, null);

            holder = new ViewHolder();
            holder.pname = (TextView)view.findViewById(R.id.match_player_name_teams);
            holder.pscore = (TextView)view.findViewById(R.id.match_player_score_teams);
            holder.img = (ImageView)view.findViewById(R.id.match_standing_teams);
            holder.tscore = (TextView)view.findViewById(R.id.match_team_score);

            background_refresh(view, position, holder);

            holder.pscore.setText("0");
            holder.pname.setText(players.get(position).getName());
            holder.img.setImageResource(R.color.Background);
            holder.tscore.setText("0");

            view.setTag(holder);
        }

        else {
            holder = (ViewHolder)view.getTag();
        }

        /* Refresh */
        holder.pscore.setText(""+players.get(position).getTOT());
        holder.tscore.setText(""+players.get(position).getTeam_TOT());
        refresh_standings(position, holder);

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View item) {

                lastclicked = position;

                Intent intent = new Intent (sActivity, Match_Score.class);
                intent.putStringArrayListExtra("expansions", exps);
                intent.putExtra("player", players.get(position).getName());
                sActivity.startActivityForResult(intent, REQUEST_TEAMS);

            }
        });

        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TEAMS) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Integer> punteggi = data.getIntegerArrayListExtra("PUNTEGGIO");

                players.get(lastclicked).setNero(-1);
                players.get(lastclicked).setLeaders(-1);
                players.get(lastclicked).setSailors(-1);

                boolean ldone = false;
                boolean sdone = false;

                for (int i=0; i<punteggi.size(); i++){
                    switch (i){
                        case 0: players.get(lastclicked).setGuerra(punteggi.get(i));
                            break;
                        case 1: players.get(lastclicked).setMonete(punteggi.get(i));
                            break;
                        case 2: players.get(lastclicked).setMeraviglia(punteggi.get(i));
                            break;
                        case 3: players.get(lastclicked).setCivilta(punteggi.get(i));
                            break;
                        case 4: players.get(lastclicked).setMercato(punteggi.get(i));
                            break;
                        case 5: players.get(lastclicked).setScienza(punteggi.get(i));
                            break;
                        case 6: players.get(lastclicked).setGilde(punteggi.get(i));
                            break;

                        //Le espansioni sono ordinate (in inclusione ed sclusione) come:
                        // CITIES -> LEADERS -> SAILORS
                        case 7:
                            if (exps.contains("Cities"))
                                players.get(lastclicked).setNero(punteggi.get(i));
                            else if (exps.contains("Leaders")) {
                                players.get(lastclicked).setLeaders(punteggi.get(i));
                                ldone = true;
                            }
                            else if (exps.contains("Sailors")) {
                                players.get(lastclicked).setSailors(punteggi.get(i));
                                sdone = true;
                            }
                            break;
                        case 8:
                            if (exps.contains("Leaders") && !ldone)
                                players.get(lastclicked).setLeaders(punteggi.get(i));
                            else if (exps.contains("Sailors") && !sdone) {
                                players.get(lastclicked).setSailors(punteggi.get(i));
                                sdone = true;
                            }
                            break;
                        case 9:
                            if (exps.contains("Sailors") && !sdone)
                                players.get(lastclicked).setSailors(punteggi.get(i));
                            break;
                        default:  Toast.makeText(sActivity, "ERROR!!", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                //Setta punteggio singolo
                Integer n=0;
                for (int i=0; i<punteggi.size();i++) n=n+punteggi.get(i);
                players.get(lastclicked).setTOT(n);

                //setta punteggio di squadra
                for (int i=0; i<players.size(); i++){
                    if (players.get(i).getTeam() == players.get(lastclicked).getTeam() && players.get(i).getName() != players.get(lastclicked).getName()){
                        Integer score = players.get(lastclicked).getTOT() + players.get(i).getTOT();
                        players.get(i).setTeam_TOT(score);
                        players.get(lastclicked).setTeam_TOT(score);
                        break;
                    }
                }

                check_standings(lastclicked);
                notifyDataSetChanged();

            }
        }
    }


    /* Cambia colori ai giocatori nella lista a seconda del team */
    private void background_refresh(View view, int position, ViewHolder holder){
        switch (players.get(position).getTeam()){
            case 1:
                view.setBackgroundResource(R.drawable.draw_team1);
                holder.tscore.setTextColor(sActivity.getResources().getColor(R.color.black));
                break;
            case 2:
                view.setBackgroundResource(R.drawable.draw_team2);
                holder.tscore.setTextColor(sActivity.getResources().getColor(R.color.black));
                break;
            case 3:
                view.setBackgroundResource(R.drawable.draw_team3);
                holder.tscore.setTextColor(sActivity.getResources().getColor(R.color.black));
                break;
            case 4:
                view.setBackgroundResource(R.drawable.draw_team4);
                holder.tscore.setTextColor(sActivity.getResources().getColor(R.color.black));
                break;
            case 5:
                view.setBackgroundResource(R.drawable.draw_team5);
                holder.tscore.setTextColor(sActivity.getResources().getColor(R.color.black));
                break;
            default:
                view.setBackgroundResource(R.color.Background);
                break;
        }
    }


    /* Si occupa della classifica dinamicamente */
    private void check_standings(int position){

        int team = players.get(position).getTeam();

        //Controlla se già esiste e elimina
        if (standings.contains(team)) {
            standings.remove(standings.indexOf(team));
        }

        //Se la classifica è vuota, è automaticamente primo
        if (standings.isEmpty()) {
            standings.add(team);
        }

        //Altrimenti controlla sequenzialmente dove posizionare l'elemento
        else {

            boolean last = true;
            boolean brk = false;

            // scorre i teams in classifica
            for (int i = 0; i < standings.size() && !brk; i++){

                // scorre i giocatori
                for (int k=0; k < players.size()  && !brk; k++){

                    // si ferma al primo con il team i in classifica
                    if (players.get(k).getTeam() == standings.get(i)) {

                        // controlla sui punteggi
                        if (players.get(position).getTeam_TOT() >= players.get(k).getTeam_TOT()) {
                            standings.add(i, team);
                            last = false;
                            brk = true;
                        }
                    }
                }
            }

            if (last) {
                standings.add(team);
            }
        }

//        Log.i("DEBUG", " -------------------- ");
//        for (int i = 0; i < standings.size(); i++) {
//            Log.i("DEBUG", "Posizione "+(i+1)+" - Team "+standings.get(i));
//        }
//        Log.i("DEBUG", " -------------------- ");

        //refresha la classifica
        for (int i = 0; i < standings.size(); i++) {
            for (int k=0; k < players.size(); k++){
                if (players.get(k).getTeam() == standings.get(i)){
                    players.get(k).setStanding(i+1);
                }
            }
        }
    }

    /* Upgrade della grafica delle posizioni */
    private void refresh_standings(int position, ViewHolder holder){

        switch (players.get(position).getStanding()){
            case 1:
                holder.img.setImageResource(R.mipmap.cup_gold);
                break;
            case 2:
                holder.img.setImageResource(R.mipmap.cup_silver);
                break;
            case 3:
                holder.img.setImageResource(R.mipmap.cup_bronze);
                break;
            case 4:
                holder.img.setImageResource(R.mipmap.place_4th);
                break;
            case 5:
                holder.img.setImageResource(R.mipmap.place_5th);
                break;
            default:
                holder.img.setImageResource(R.color.Background);
                break;
        }
    }


    private static class ViewHolder{
        public TextView pname;
        public TextView pscore;
        public TextView tscore;
        public ImageView img;
    }
}
