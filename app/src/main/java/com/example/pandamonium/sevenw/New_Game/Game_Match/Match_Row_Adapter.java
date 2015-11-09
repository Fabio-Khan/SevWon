package com.example.pandamonium.sevenw.New_Game.Game_Match;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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

/**
 * Created by Pandamonium on 02/10/2015.
 */

public class Match_Row_Adapter extends BaseAdapter {

    private Activity sActivity;
    private ArrayList<Player> players;
    private ArrayList<String> exps = new ArrayList<>();

    //supporto per la classifica
    private ArrayList<Integer> standings;
    public static final Integer REQUEST_MATCH = 1;
    private int lastclicked;

    public Match_Row_Adapter(Activity sActivity, ArrayList<Player> players, ArrayList<String> exps) {
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
            view = li.inflate(R.layout.game_match_row, null);

            holder = new ViewHolder();
            holder.pname = (TextView)view.findViewById(R.id.match_player_name);
            holder.pscore = (TextView)view.findViewById(R.id.match_player_score);
            holder.img = (ImageView)view.findViewById(R.id.match_standing);

            holder.pscore.setText("0");
            holder.pname.setText(players.get(position).getName());
            holder.img.setImageResource(R.color.Background);

            view.setTag(holder);
        }

        else {
            holder = (ViewHolder)view.getTag();
        }


        holder.pscore.setText(((Integer) (players.get(position).getTOT())).toString());
        refresh_standings(position, holder);


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View item) {

                lastclicked = position;

                //TODO implementa il passaggio di scores così che il punteggio è consistente
//                ArrayList<Integer> scores = new ArrayList<Integer>();

                Intent intent = new Intent (sActivity, Match_Score.class);
                intent.putStringArrayListExtra("expansions", exps);
                intent.putExtra("player", players.get(position).getName());
                sActivity.startActivityForResult(intent, REQUEST_MATCH);

            }
        });

        return view;
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MATCH) {
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

//                Log.i("DEBUG", players.get(lastclicked).getName()+": "
//                        +players.get(lastclicked).getGuerra()+" - "
//                        +players.get(lastclicked).getMonete()+" - "
//                        +players.get(lastclicked).getMeraviglia()+" - "
//                        +players.get(lastclicked).getCivilta()+" - "
//                        +players.get(lastclicked).getMercato()+" - "
//                        +players.get(lastclicked).getGilde()+" - "
//                        +players.get(lastclicked).getScienza()+" - "
//                        +players.get(lastclicked).getNero()+" - "
//                        +players.get(lastclicked).getLeaders()+" - "
//                        +players.get(lastclicked).getSailors());
                Integer n=0;
                for (int i=0; i<punteggi.size();i++) n=n+punteggi.get(i);
                players.get(lastclicked).setTOT(n);

                check_standings(lastclicked);

                notifyDataSetChanged();

            }
        }
    }


    /* Si occupa della classifica dinamicamente */
    private void check_standings(int position){


            //Controlla se già esiste e elimina
        if (standings.contains(position)) {
            standings.remove(standings.indexOf(position));
        }

        //Se la classifica è vuota, è automaticamente primo
        if (standings.isEmpty()) {
            standings.add(position);
        }

        //Altrimenti controlla sequenzialmente dove posizionare l'elemento
        else {

            boolean last = true;

            for (int i = 0; i < standings.size(); i++)
                if (players.get(position).getTOT() >= players.get(standings.get(i)).getTOT()) {
                    standings.add(i, position);
                    last = false;
                    break;
                }

            if (last) {
                standings.add(position);
                }
        }

        //refresha la classifica
        for (int i = 0; i < standings.size(); i++) {
            if (i != 0 && players.get(standings.get(i)).getTOT() == players.get(standings.get(i - 1)).getTOT()) {
                players.get(standings.get(i)).setStanding(i);
            } else {
                players.get(standings.get(i)).setStanding(i + 1);
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
            case 6:
                holder.img.setImageResource(R.mipmap.place_6th);
                break;
            case 7:
                holder.img.setImageResource(R.mipmap.place_7th);
                break;
            case 8:
                holder.img.setImageResource(R.mipmap.place_8th);
                break;
            case 9:
                holder.img.setImageResource(R.mipmap.place_9th);
                break;
            case 10:
                holder.img.setImageResource(R.mipmap.place_10th);
                break;
            default:
                holder.img.setImageResource(R.color.Background);
                break;
        }
    }


    private static class ViewHolder{
        public TextView pname;
        public TextView pscore;
        public ImageView img;
    }

}
