package com.example.pandamonium.sevenw.Players;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pandamonium.sevenw.R;
import com.example.pandamonium.sevenw.Support.Database.DBManager;
import com.example.pandamonium.sevenw.Support.Profile;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 04/11/2015.
 */
public class Players_adapter extends BaseAdapter {

    private DBManager db;
    private Activity sActivity;
    private ArrayList<Profile> players = new ArrayList<>();

    public Players_adapter(Activity sActivity, ArrayList<Profile> players, DBManager db) {
        this.db = db;
        this.sActivity = sActivity;
        this.players = players;
    }

    public void setPlayers (ArrayList<Profile> players){ this.players=players; }

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
        ViewHolder holder;

        if(view == null) {
            LayoutInflater li = sActivity.getLayoutInflater();
            view = li.inflate(R.layout.players_main_item, null);

            holder = new ViewHolder();
            holder.text = (TextView)view.findViewById(R.id.playerslist_text);
            holder.img = (ImageView)view.findViewById(R.id.playerslist_img);

            view.setTag(holder);
        }

        else {
            holder = (ViewHolder)view.getTag();
        }

        holder.img.setImageResource(R.mipmap.player);
        holder.text.setText(players.get(position).getName());

        view.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View item) {

                PopupMenu popup = new PopupMenu(sActivity, item);

                popup.getMenu().add(1,1,1,"DELETE "+players.get(position).getName());
                popup.getMenu().add(1,2,2,"BACK");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case 1:
                                db.delete_player(players.get(position).getName());
                                players = db.query_players();
                                notifyDataSetChanged();
                                break;
                            case 2:
                                //do Nothing
                                break;
                            default:
                                Toast.makeText(sActivity, "Errore PLAYERS ADAPTER", Toast.LENGTH_SHORT).show();
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
        public TextView text;
        public ImageView img;
    }

}
