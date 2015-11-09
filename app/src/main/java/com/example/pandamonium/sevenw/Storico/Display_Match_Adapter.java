package com.example.pandamonium.sevenw.Storico;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandamonium.sevenw.R;
import com.example.pandamonium.sevenw.Support.Player;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 23/10/2015.
 */
public class Display_Match_Adapter extends BaseAdapter{

    private boolean teams;
    private Activity sActivity;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Integer> scores = new ArrayList<>();

    private boolean cities;
    private boolean leaders;
    private boolean sailors;

    public Display_Match_Adapter (Activity context, ArrayList<Player> players, boolean teams){

        this.teams = teams;
        this.sActivity = context;
        this.players = players;

        this.cities = false;
        this.leaders = false;
        this.sailors = false;

        set_up_scores();

    }

    /* Numero di righe, ossia di punteggi da segnare per giocatore */
    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int position) {
        return scores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout view = (LinearLayout)convertView;
        ViewHolder holder;

        if(view == null) {
            LayoutInflater li = sActivity.getLayoutInflater();
            view = (LinearLayout)li.inflate(R.layout.history_display_match_item, null);

            holder = new ViewHolder();
            holder.text = new ArrayList<>();

            holder.img = (ImageView) view.findViewById(R.id.history_match_score_img);

            build_layout(view,holder);

            view.setTag(holder);
        }

        else {
            holder = (ViewHolder)view.getTag();
        }


        draw_line(holder, position);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View item) {

            }
        });

        return view;
    }


    /* Numero di colonne della matrice */
    private void build_layout (LinearLayout view, ViewHolder holder){

        for (int i=0; i<players.size(); i++){

            TextView textView = new TextView(sActivity);

            //FIXME no hardcoding
            textView.setHeight(35);
            textView.setWidth(70);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize( 20 );


            switch(i){
                case 0: textView.setId(R.id.text1);
                    view.addView(textView);
                    holder.text.add((TextView) view.findViewById(R.id.text1));
                    break;
                case 1: textView.setId(R.id.text2);
                    view.addView(textView);
                    holder.text.add((TextView) view.findViewById(R.id.text2));
                    break;
                case 2: textView.setId(R.id.text3);
                    view.addView(textView);
                    holder.text.add((TextView) view.findViewById(R.id.text3));
                    break;
                case 3: textView.setId(R.id.text4);
                    view.addView(textView);
                    holder.text.add((TextView) view.findViewById(R.id.text4));
                    break;
                case 4: textView.setId(R.id.text5);
                    view.addView(textView);
                    holder.text.add((TextView) view.findViewById(R.id.text5));
                    break;
                case 5: textView.setId(R.id.text6);
                    view.addView(textView);
                    holder.text.add((TextView) view.findViewById(R.id.text6));
                    break;
                case 6: textView.setId(R.id.text7);
                    view.addView(textView);
                    holder.text.add((TextView) view.findViewById(R.id.text7));
                    break;
                case 7: textView.setId(R.id.text8);
                    view.addView(textView);
                    holder.text.add((TextView) view.findViewById(R.id.text8));
                    break;
                case 8: textView.setId(R.id.text9);
                    view.addView(textView);
                    holder.text.add((TextView) view.findViewById(R.id.text9));
                    break;
                default: Toast.makeText(sActivity, "ERROR!! - build_layout", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    /* Numero di righe della matrice 13 max*/
    private void set_up_scores(){

        // per i nomi
        scores.add(0);

        //aggiungo 7 posti per i punteggi base
        for (int i=0; i<7; i++) scores.add(0);

        //gestisco espansioni
        if (players.get(0).getNero() >= 0) {
            scores.add(0);
            cities = true;
        }

        if (players.get(0).getLeaders() >= 0) {
            scores.add(0);
            leaders = true;
        }

        if (players.get(0).getSailors() >= 0) {
            scores.add(0);
            sailors = true;
        }

        //aggiungo il totale (NO TEAMS)
        scores.add(0);

        //aggiungo il totale (TEAMS)
        if(teams) scores.add(0);

    }


    /**position è la posizione all'interno di scores, utilizzata per indicizzare le colonne della
     * tabella. Bisogna fare attenzione alla gestione delle espansioni.
     */
    private void draw_line(ViewHolder holder, int position){

        switch (position){
            case 0: //nome
                holder.img.setImageResource(R.drawable.history_player_logo);
                for (int i=0; i<players.size(); i++){
                    holder.text.get(i).setTextSize(10);
                    holder.text.get(i).setWidth(70);
                    holder.text.get(i).setText( players.get(i).getName() );
                    holder.text.get(i).setBackgroundResource( R.drawable.history_player );
                }
                break;

            case 1: //guerra
                holder.img.setImageResource(R.drawable.history_guerra_logo);
                for (int i=0; i<players.size(); i++){
                    holder.text.get(i).setText( ""+players.get(i).getGuerra() );
                    holder.text.get(i).setBackgroundResource(R.drawable.history_guerra);
                }
                break;

            case 2: //monete
                holder.img.setImageResource(R.drawable.history_monete_logo);
                for (int i=0; i<players.size(); i++){
                    holder.text.get(i).setText( ""+players.get(i).getMonete() );
                    holder.text.get(i).setBackgroundResource(R.drawable.history_monete);
                }
                break;

            case 3: //meraviglie
                holder.img.setImageResource(R.drawable.history_meraviglia_logo);
                for (int i=0; i<players.size(); i++){
                    holder.text.get(i).setText( ""+players.get(i).getMeraviglia() );
                    holder.text.get(i).setBackgroundResource(R.drawable.history_meraviglia);
                }
                break;

            case 4: //civiltà
                holder.img.setImageResource(R.drawable.history_civilta_logo);
                for (int i=0; i<players.size(); i++){
                    holder.text.get(i).setText( ""+players.get(i).getCivilta() );
                    holder.text.get(i).setBackgroundResource(R.drawable.history_civilta);
                }
                break;

            case 5: //mercato
                holder.img.setImageResource(R.drawable.history_mercato_logo);
                for (int i=0; i<players.size(); i++){
                    holder.text.get(i).setText( ""+players.get(i).getMercato() );
                    holder.text.get(i).setBackgroundResource(R.drawable.history_mercato);
                }
                break;

            case 6: //scienza
                holder.img.setImageResource(R.drawable.history_scienza_logo);
                for (int i=0; i<players.size(); i++){
                    holder.text.get(i).setText( ""+players.get(i).getGilde() );
                    holder.text.get(i).setBackgroundResource(R.drawable.history_scienza);
                }
                break;

            case 7: //gilde
                holder.img.setImageResource(R.drawable.history_gilde_logo);
                for (int i=0; i<players.size(); i++){
                    holder.text.get(i).setText( ""+players.get(i).getScienza() );
                    holder.text.get(i).setBackgroundResource(R.drawable.history_gilde);
                }
                break;

            case 8:
                if (cities){
                    holder.img.setImageResource(R.drawable.history_cities_logo);
                    for (int i=0; i<players.size(); i++){
                        holder.text.get(i).setText( ""+players.get(i).getNero() );
                        holder.text.get(i).setBackgroundResource(R.drawable.history_cities);
                    }
                }
                else if (leaders){
                    holder.img.setImageResource(R.drawable.history_leaders_logo);
                    for (int i=0; i<players.size(); i++){
                        holder.text.get(i).setText( ""+players.get(i).getLeaders() );
                        holder.text.get(i).setBackgroundResource(R.drawable.history_leaders);
                    }
                }
                else if (sailors){
                    holder.img.setImageResource(R.drawable.history_sailors_logo);
                    for (int i=0; i<players.size(); i++){
                        holder.text.get(i).setText( ""+players.get(i).getSailors() );
                        holder.text.get(i).setBackgroundResource(R.drawable.history_sailors);
                    }
                }
                else{
                    holder.img.setImageResource(R.drawable.history_sum);
                    for (int i=0; i<players.size(); i++){
                        holder.text.get(i).setText( ""+players.get(i).getTOT() );
                        holder.text.get(i).setBackgroundResource(R.drawable.history_single);
                    }
                }
                break;

            case 9:
                if (leaders && cities){
                    holder.img.setImageResource(R.drawable.history_leaders_logo);
                for (int i=0; i<players.size(); i++){
                    holder.text.get(i).setText( ""+players.get(i).getLeaders() );
                    holder.text.get(i).setBackgroundResource(R.drawable.history_leaders);

                    }

                }
                else if (sailors && !cities && leaders || sailors && cities){
                    holder.img.setImageResource(R.drawable.history_sailors_logo);
                    for (int i=0; i<players.size(); i++){
                        holder.text.get(i).setText( ""+players.get(i).getSailors() );
                        holder.text.get(i).setBackgroundResource(R.drawable.history_sailors);
                    }

                }
                else {
                    holder.img.setImageResource(R.drawable.history_sum);
                    for (int i=0; i<players.size(); i++){
                        holder.text.get(i).setText( ""+players.get(i).getTOT() );
                        holder.text.get(i).setBackgroundResource(R.drawable.history_single);
                    }
                }
                break;

            case 10:
                if (sailors && cities && leaders){
                    holder.img.setImageResource(R.drawable.history_sailors_logo);
                for (int i=0; i<players.size(); i++){
                    holder.text.get(i).setText( ""+players.get(i).getSailors() );
                    holder.text.get(i).setBackgroundResource(R.drawable.history_sailors);
                    }
                }
                else{
                    holder.img.setImageResource(R.drawable.history_sum);
                    for (int i=0; i<players.size(); i++){
                        holder.text.get(i).setText( ""+players.get(i).getTOT() );
                        holder.text.get(i).setBackgroundResource(R.drawable.history_single);
                    }
                }
                break;

            case 11:
                holder.img.setImageResource(R.drawable.history_sum);
                for (int i=0; i<players.size(); i++){
                    holder.text.get(i).setText( ""+players.get(i).getTOT() );
                    holder.text.get(i).setBackgroundResource(R.drawable.history_single);
                }
                break;

            case 12:
                holder.img.setImageResource(R.drawable.history_sum);
                int tm = 0;
                for (int i=0; i<players.size(); i=i+2){
                    if(players.get(i).getTeam() == players.get(i+1).getTeam()) {
                        holder.text.get(tm).setWidth(140);
                        holder.text.get(tm).setText("" + players.get(i).getTeam_TOT());
                        holder.text.get(tm).setBackgroundResource(R.drawable.history_teams);
                        tm++;
                    }
                }
                break;

            default: Toast.makeText(sActivity, "ERROR!! - draw_line", Toast.LENGTH_SHORT).show();
                break;
        }

    }


    private static class ViewHolder{
        public ImageView img;
        public ArrayList<TextView> text;
    }

}
