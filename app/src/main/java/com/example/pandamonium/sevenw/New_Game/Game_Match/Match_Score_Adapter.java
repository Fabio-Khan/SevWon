package com.example.pandamonium.sevenw.New_Game.Game_Match;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pandamonium.sevenw.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Pandamonium on 08/10/2015.
 */
public class Match_Score_Adapter extends BaseAdapter {

    private Activity sActivity;
    private ArrayList<String> points = new ArrayList<>();
    private ArrayList<Integer> score = new ArrayList<>();

    public Match_Score_Adapter(Activity sActivity, ArrayList<String> points, ArrayList<Integer> score) {
        this.points = points;
        this.sActivity = sActivity;
        this.score = score;
    }


    @Override
    public int getCount() {
        return points.size();
    }

    @Override
    public Object getItem(int position) {
        return points.get(position);
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
            view = li.inflate(R.layout.game_scoring_item, null);

            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.score_txt);
            holder.img = (ImageView) view.findViewById(R.id.score_img);
            holder.scoreitem = (LinearLayout) view.findViewById(R.id.scoreitem);

            view.setTag(holder);
        }

        else {
            holder = (ViewHolder)view.getTag();
        }


        set_images(holder, position);


        /* Evento alla selezione del bottone */
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View item) {

                // TODO implementare politica di Inserzione del punteggio.
                Random rand = new Random();
                score.set(position, rand.nextInt(50));

                holder.text.setText(score.get(position).toString());
                notifyDataSetChanged();

            }
        });

        return view;
    }


    private void set_images(ViewHolder holder, int position){

        switch (points.get(position)){
            case "guerra":
                holder.scoreitem.setBackgroundResource(R.drawable.score_guerra);
                break;
            case "monete":
                holder.scoreitem.setBackgroundResource(R.drawable.score_monete);
                break;
            case "meraviglia":
                holder.scoreitem.setBackgroundResource(R.drawable.score_meraviglia);
                break;
            case "civilta":
                holder.scoreitem.setBackgroundResource(R.drawable.score_civilta);
                break;
            case "mercato":
                holder.scoreitem.setBackgroundResource(R.drawable.score_mercato);
                break;
            case "scienza":
                holder.scoreitem.setBackgroundResource(R.drawable.score_scienza);
                break;
            case "gilde":
                holder.scoreitem.setBackgroundResource(R.drawable.score_gilde);
                break;
            case "nero":
                holder.scoreitem.setBackgroundResource(R.drawable.score_nere);
                break;
            case "exp_leaders":
                holder.scoreitem.setBackgroundResource(R.drawable.score_leaders);
                break;
            case "exp_sailors":
                holder.scoreitem.setBackgroundResource(R.drawable.score_sailors);
                break;
            default:
                holder.scoreitem.setBackgroundResource(R.mipmap.ic_launcher);
                break;
        }

    }


    private static class ViewHolder{
        public TextView text;
        public ImageView img;
        public LinearLayout scoreitem;
    }
}
