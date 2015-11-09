package com.example.pandamonium.sevenw.Storico;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandamonium.sevenw.New_Game.Game_Match.Match_Activity;
import com.example.pandamonium.sevenw.R;
import com.example.pandamonium.sevenw.Support.Database.DBManager;
import com.example.pandamonium.sevenw.Support.Match;

import java.net.ContentHandler;
import java.util.ArrayList;

/**
 * Created by Pandamonium on 15/10/2015.
 */
public class Storico_Adapter extends BaseAdapter {

    private Activity sActivity;
    private ArrayList<Match> history = new ArrayList<>();
    private boolean teams;
    private DBManager db;

    public Storico_Adapter(Activity context, ArrayList<Match> history, DBManager db){

        this.sActivity = context;
        this.history = history;
        this.teams = false;

        this.db = db;
    }

    public void set_history(ArrayList<Match> history){
        this.history = history;
    }
    public void set_teams(boolean teams){
        this.teams=teams;
    }

    @Override
    public int getCount() {
        return history.size();
    }

    @Override
    public Object getItem(int position) {
        return history.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        if(view == null) {
            LayoutInflater li = sActivity.getLayoutInflater();
            view = li.inflate(R.layout.history_item, null);

            holder = new ViewHolder();
            holder.date = (TextView)view.findViewById(R.id.match_date);
            holder.num_match = (TextView)view.findViewById(R.id.match_number);
            holder.numplayers = (TextView)view.findViewById(R.id.match_players);

            holder.date.setText("0");
            holder.num_match.setText("0");
            holder.numplayers.setText("0");

            view.setTag(holder);
        }

        else {
            holder = (ViewHolder)view.getTag();
        }

        holder.date.setText(""+history.get(position).getDate());
        holder.num_match.setText(""+history.get(position).getNumber());
        holder.numplayers.setText(""+history.get(position).getPlayers().size());

        /* Quando un item viene cliccato */
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View item) {

                //TODO quando si clicca evidenzialo.
                //Ci passo la data e il numero per identificare univocamente il match.
                Intent intent = new Intent(sActivity, Display_Match.class);
                intent.putExtra("date", history.get(position).getDate());
                intent.putExtra("number", history.get(position).getNumber());
                intent.putExtra("teams", teams);
                sActivity.startActivity(intent);
            }

        });

        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View item) {

                PopupMenu popup = new PopupMenu(sActivity, item);

                popup.getMenu().add(1, 1, 1, "DELETE "+history.get(position).getDate()+" - "+history.get(position).getNumber());
                popup.getMenu().add(1, 2, 2, "BACK");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case 1:
                                Match delma = history.get(position);
                                delma.setTeams(teams);
                                db.delete_match(delma);
                                history.remove(position);
                                notifyDataSetChanged();
                                break;
                            case 2:
                                //do Nothing
                                break;
                            default:
                                Toast.makeText(sActivity, "Errore Storico Adapter!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });

                popup.show();

                return true;
            }
        });

        return view;
    }


    private static class ViewHolder{
        public TextView date;
        public TextView num_match;
        public TextView numplayers;
    }
}
