package com.example.pandamonium.sevenw.Statistics;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pandamonium.sevenw.R;
import com.example.pandamonium.sevenw.Support.Profile;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 08/11/2015.
 */
public class Statistics_Scores extends BaseAdapter {

    private Activity sActivity;

    private ArrayList<String> player = new ArrayList<>();
    private ArrayList<Integer> score = new ArrayList<>();
    private ArrayList<Integer> dates = new ArrayList<>();

    public Statistics_Scores(Activity sActivity, ArrayList<String> player, ArrayList<Integer> score, ArrayList<Integer> dates) {
        this.sActivity = sActivity;
        this.player=player;
        this.score=score;
        this.dates=dates;
    }


    public ArrayList<String> getPlayer() {
        return player;
    }

    public void setPlayer(ArrayList<String> player) {
        this.player = player;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }

    public void setScore(ArrayList<Integer> score) {
        this.score = score;
    }

    public ArrayList<Integer> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Integer> dates) {
        this.dates = dates;
    }

    @Override
    public int getCount() {
        return player.size();
    }

    @Override
    public Object getItem(int position) {
        return player.get(position);
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
            view = li.inflate(R.layout.statistics_item_scores, null);

            holder = new ViewHolder();
            holder.img_top = (ImageView)view.findViewById(R.id.stat_top);
            holder.text_player = (TextView)view.findViewById(R.id.stat_top_name);
            holder.text_top = (TextView)view.findViewById(R.id.stat_top_score);
            holder.text_date = (TextView)view.findViewById(R.id.stat_top_date);

            view.setTag(holder);
        }

        else {
            holder = (ViewHolder)view.getTag();
        }

        populate(holder, position);

        return view;
    }


    private static class ViewHolder{
        public ImageView img_top;
        public TextView text_player;
        public TextView text_top;
        public TextView text_date;
    }


    private void populate(ViewHolder holder, int position){
        switch(position){
            case 0:
                holder.img_top.setImageResource(R.mipmap.cup_gold);
                break;
            case 1:
                holder.img_top.setImageResource(R.mipmap.cup_silver);
                break;
            case 2:
                holder.img_top.setImageResource(R.mipmap.cup_bronze);
                break;
            case 3:
                holder.img_top.setImageResource(R.mipmap.place_4th);
                break;
            case 4:
                holder.img_top.setImageResource(R.mipmap.place_5th);
                break;
            case 5:
                holder.img_top.setImageResource(R.mipmap.place_6th);
                break;
            case 6:
                holder.img_top.setImageResource(R.mipmap.place_7th);
                break;
            case 7:
                holder.img_top.setImageResource(R.mipmap.place_8th);
                break;
            case 8:
                holder.img_top.setImageResource(R.mipmap.place_9th);
                break;
            case 9:
                holder.img_top.setImageResource(R.mipmap.place_10th);
                break;
            default:
                holder.img_top.setImageResource(R.color.Background);
                break;
        }

        holder.text_player.setText(player.get(position));
        holder.text_top.setText(""+score.get(position));
        holder.text_date.setText(""+dates.get(position));

    }
}
