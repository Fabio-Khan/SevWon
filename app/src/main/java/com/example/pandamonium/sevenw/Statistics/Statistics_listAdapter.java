package com.example.pandamonium.sevenw.Statistics;

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
import com.example.pandamonium.sevenw.Support.Profile;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 07/11/2015.
 */
public class Statistics_listAdapter extends BaseAdapter {

    private Activity sActivity;
    private ArrayList<Profile> players = new ArrayList<>();

    public Statistics_listAdapter(Activity sActivity, ArrayList<Profile> players) {
        this.sActivity = sActivity;
        this.players = players;
    }

    public ArrayList<Profile> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Profile> players) {
        this.players = players;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        if(view == null) {
            LayoutInflater li = sActivity.getLayoutInflater();
            view = li.inflate(R.layout.statistics_item_players, null);

            holder = new ViewHolder();
            holder.img_standing = (ImageView)view.findViewById(R.id.stat_standing);
            holder.text_player = (TextView)view.findViewById(R.id.stat_player_name);
            holder.text_1st = (TextView)view.findViewById(R.id.stat_player_score_1st);
            holder.img_1st = (ImageView)view.findViewById(R.id.stat_standing_1st);
            holder.text_2nd = (TextView)view.findViewById(R.id.stat_player_score_2nd);
            holder.img_2nd = (ImageView)view.findViewById(R.id.stat_standing_2nd);
            holder.text_3rd = (TextView)view.findViewById(R.id.stat_player_score_3rd);
            holder.img_3rd = (ImageView)view.findViewById(R.id.stat_standing_3rd);

            view.setTag(holder);
        }

        else {
            holder = (ViewHolder)view.getTag();
        }

        players_standings(holder, position);

        return view;
    }


    private static class ViewHolder{
        public ImageView img_standing;
        public TextView text_player;

        public ImageView img_1st;
        public TextView text_1st;
        public ImageView img_2nd;
        public TextView text_2nd;
        public ImageView img_3rd;
        public TextView text_3rd;
    }


    private void players_standings(ViewHolder holder, int position){

        switch(position){
            case 0:
                holder.img_standing.setImageResource(R.mipmap.cup_gold);
                break;
            case 1:
                holder.img_standing.setImageResource(R.mipmap.cup_silver);
                break;
            case 2:
                holder.img_standing.setImageResource(R.mipmap.cup_bronze);
                break;
            case 3:
                holder.img_standing.setImageResource(R.mipmap.place_4th);
                break;
            case 4:
                holder.img_standing.setImageResource(R.mipmap.place_5th);
                break;
            case 5:
                holder.img_standing.setImageResource(R.mipmap.place_6th);
                break;
            case 6:
                holder.img_standing.setImageResource(R.mipmap.place_7th);
                break;
            case 7:
                holder.img_standing.setImageResource(R.mipmap.place_8th);
                break;
            case 8:
                holder.img_standing.setImageResource(R.mipmap.place_9th);
                break;
            case 9:
                holder.img_standing.setImageResource(R.mipmap.place_10th);
                break;
            default:
                holder.img_standing.setImageResource(R.color.Background);
                break;
        }

        holder.text_player.setText(players.get(position).getName());
        holder.img_1st.setImageResource(R.mipmap.cup_gold);
        holder.text_1st.setText("" + players.get(position).getWins());
        holder.img_2nd.setImageResource(R.mipmap.cup_silver);
        holder.text_2nd.setText("" + players.get(position).getSecond());
        holder.img_3rd.setImageResource(R.mipmap.cup_bronze);
        holder.text_3rd.setText("" + players.get(position).getThird());

    }

}
