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
public class Statistics_Categories extends BaseAdapter {

    private Activity sActivity;
    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<String> player = new ArrayList<>();
    private ArrayList<Integer> score = new ArrayList<>();
    private ArrayList<Integer> dates = new ArrayList<>();

    public Statistics_Categories(Activity sActivity, ArrayList<String> categories, ArrayList<String> player, ArrayList<Integer> score, ArrayList<Integer> dates) {
        this.sActivity = sActivity;
        this.categories = categories;
        this.categories=categories;
        this.player=player;
        this.score=score;
        this.dates=dates;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }

    public void setScore(ArrayList<Integer> score) {
        this.score = score;
    }

    public ArrayList<String> getPlayer() {
        return player;
    }

    public void setPlayer(ArrayList<String> player) {
        this.player = player;
    }

    public ArrayList<Integer> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Integer> dates) {
        this.dates = dates;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
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
            view = li.inflate(R.layout.statistics_item_category, null);

            holder = new ViewHolder();
            holder.img_category = (ImageView)view.findViewById(R.id.stat_category);
            holder.text_player = (TextView)view.findViewById(R.id.stat_category_name);
            holder.text_category = (TextView)view.findViewById(R.id.stat_category_score);
            holder.text_date = (TextView)view.findViewById(R.id.stat_category_date);

            view.setTag(holder);
        }

        else {
            holder = (ViewHolder)view.getTag();
        }

        populate(holder, position);

        return view;
    }


    private static class ViewHolder{
        public ImageView img_category;
        public TextView text_player;
        public TextView text_category;
        public TextView text_date;
    }


    private void populate(ViewHolder holder, int position){
        switch(position){
            case 0:
                holder.img_category.setImageResource(R.drawable.history_guerra_logo);
                break;
            case 1:
                holder.img_category.setImageResource(R.drawable.history_monete_logo);
                break;
            case 2:
                holder.img_category.setImageResource(R.drawable.history_meraviglia_logo);
                break;
            case 3:
                holder.img_category.setImageResource(R.drawable.history_civilta_logo);
                break;
            case 4:
                holder.img_category.setImageResource(R.drawable.history_mercato_logo);
                break;
            case 5:
                holder.img_category.setImageResource(R.drawable.history_scienza_logo);
                break;
            case 6:
                holder.img_category.setImageResource(R.drawable.history_gilde_logo);
                break;
            case 7:
                holder.img_category.setImageResource(R.drawable.history_cities_logo);
                break;
            case 8:
                holder.img_category.setImageResource(R.drawable.history_leaders_logo);
                break;
            case 9:
                holder.img_category.setImageResource(R.drawable.history_sailors_logo);
                break;
            default:
                holder.img_category.setImageResource(R.color.Background);
                break;
        }

        if (score.get(position) != 0) {
            holder.text_player.setText(player.get(position));
            holder.text_category.setText("" + score.get(position));
            holder.text_date.setText("" + dates.get(position));
        }
        else{
            holder.text_player.setText("");
            holder.text_category.setText("");
            holder.text_date.setText("");
        }

    }

}
