package com.example.pandamonium.sevenw.New_Game.Players_Selection;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.pandamonium.sevenw.Support.Player;
import com.example.pandamonium.sevenw.R;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 23/09/2015.
 */

public class GridAdapter_Players extends BaseAdapter {

    // Lista giocatori disponibili
    private ArrayList<Player> players;

    private Activity activity_context;

    //giocatori massimi e selezionati attualmente.
    private int maxP;
    private int selectedP;

    // Variabile e contatore per modalità Teams
    private boolean teams;
    private int CT1;
    private int CT2;
    private int CT3;
    private int CT4;

    // Per gestire i messaggi in broadcast
    public static final String INTENT_ACTION = "UPDATE_PLAYERS";
    public static final String INTENT_EXTRA  = "UP";
    public static final String INTENT_THING  = "TEAMS_SEL";


    public GridAdapter_Players(Activity sActivity, ArrayList<Player> players, int maxPL, boolean teams) {
        this.activity_context = sActivity;
        this.players = players;
        this.maxP = maxPL;
        this.selectedP = 0;

        this.teams = teams;
        this.CT1 = 0;
        this.CT2 = 0;
        this.CT3 = 0;
        this.CT4 = 0;
    }

    public void setMaxP(int max){
        this.maxP=max;
    }

    public int getSelectedP(){
        return selectedP;
    }

    public void Reset_P(){
        this.selectedP = 0;
        this.CT1 = 0;
        this.CT2 = 0;
        this.CT3 = 0;
        this.CT4 = 0;
    }

    public int getCT1(){ return this.CT1; }
    public int getCT2(){ return this.CT2; }
    public int getCT3(){ return this.CT3; }
    public int getCT4(){ return this.CT4; }

    public void setTeams(boolean tm){
        this.teams = tm;
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

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final ViewHolder holder;

        if(view == null) {
            LayoutInflater li = activity_context.getLayoutInflater();
            view = li.inflate(R.layout.game_plsel_item, null);

            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.griditem_text);
            holder.img = (ImageView) view.findViewById(R.id.griditem_image);

            view.setTag(holder);
        }

        else {
            holder = (ViewHolder)view.getTag();
        }

        /* View Refresh */
        holder.text.setText(players.get(position).getName());
        holder.img.setImageResource(players.get(position).getImage());
        View_Refresh(view, position);

        /* Evento alla selezione del bottone */
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View item) {

                /* Si possono scegliere fino ad allowedP giocatori */
                if (!item.isSelected() && !players.get(position).isSelected()) {
                    if (selectedP != maxP) {
                        Selmgm(item, position, holder);
                        item.setSelected(true);
                        players.get(position).setSelected(true);
                        selectedP++;
                    }
                } else {
                    item.setBackgroundResource(R.drawable.draw_rect);
                    item.setSelected(false);
                    players.get(position).setSelected(false);
                    if (selectedP != 0) {
                        selectedP--;
                        if(teams){
                            switch (players.get(position).getTeam()){
                                case 1:
                                    CT1--;
                                    break;
                                case 2:
                                    CT2--;
                                    break;
                                case 3:
                                    CT3--;
                                    break;
                                case 4:
                                    CT4--;
                                    break;
                                default:
                                    //ERROR!
                                    break;
                            }
                        }
                    }
                    players.get(position).setTeam(0);
                }

                Intent intent = new Intent();
                intent.setAction(INTENT_ACTION);
                intent.putExtra(INTENT_EXTRA, getSelectedP());
                intent.putExtra(INTENT_THING, teams);
                activity_context.getApplicationContext().sendBroadcast(intent);

            }

        });

        return view;
    }


    /* Gestisce la selezione dell'item */
    private void Selmgm(View  view, int position, ViewHolder holder){

        if(teams){
            PopUp_team(view, position, holder);
        }

        else {
            view.setBackgroundResource(R.drawable.draw_rect_sel);
        }
    }


    /* Gestisce il popup nella selezione a squadre */
    private void PopUp_team(final View view, final int position, final ViewHolder holder){

        PopupMenu popup = new PopupMenu(activity_context, view);

        /* Se si raggiunge il numero massimo di giocatori per team, non si può scegliere */
        /* La scelta del team è rappresentata dal suo ID */
        if (0 <= CT1 && CT1 <2){
            popup.getMenu().add(1,1,1,"Team 1");
        }
        if (0 <= CT2 && CT2 <2){
            popup.getMenu().add(1,2,2,"Team 2");
        }
        if (0 <= CT3 && CT3 <2){
            popup.getMenu().add(1,3,3,"Team 3");
        }
        if (0 <= CT4 && CT4 <2){
            popup.getMenu().add(1,4,4,"Team 4");
        }

        /* Setta il team per il giocatore selezionato */
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                players.get(position).setTeam(item.getItemId());

                int skin;

                switch (item.getItemId()) {
                    case 1:
                        skin = R.drawable.draw_team1;
                        CT1++;
                        break;
                    case 2:
                        skin = R.drawable.draw_team2;
                        CT2++;
                        break;
                    case 3:
                        skin = R.drawable.draw_team3;
                        CT3++;
                        break;
                    case 4:
                        skin = R.drawable.draw_team4;
                        CT4++;
                        break;
                    default:
                        skin = R.drawable.draw_rect_sel;
                        break;
                }

                view.setBackgroundResource(skin);

                return true;
            }
        });

        popup.show();

    }

    private void View_Refresh (View view, int position){

        if(players.get(position).isSelected()){

            if(teams){
                switch (players.get(position).getTeam()) {
                    case 1:
                        view.setBackgroundResource(R.drawable.draw_team1);
                        break;
                    case 2:
                        view.setBackgroundResource(R.drawable.draw_team2);
                        break;
                    case 3:
                        view.setBackgroundResource(R.drawable.draw_team3);
                        break;
                    case 4:
                        view.setBackgroundResource(R.drawable.draw_team4);
                        break;
                    default:
                        //ERROR!
                        break;
                }
            }

            else{
                view.setBackgroundResource(R.drawable.draw_rect_sel);
            }
        }

        else{
            view.setBackgroundResource(R.drawable.draw_rect);
        }
    }

    private static class ViewHolder{
        public TextView text;
        public ImageView img;
    }

 }
