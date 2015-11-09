package com.example.pandamonium.sevenw.New_Game.Expansions_Selection;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.pandamonium.sevenw.Support.Expansion;
import com.example.pandamonium.sevenw.R;

import java.util.ArrayList;

/**
 * Created by Pandamonium on 17/09/2015.
 */

public class ListAdapter extends BaseAdapter {

    private ArrayList <Expansion> exps;
    private Activity sActivity;


    public ListAdapter(Activity sActivity, ArrayList<Expansion> exps) {
        this.sActivity = sActivity;
        this.exps = exps;
    }

    /* You must override the methods form BaseAdpter class. */

    /* This method returns the total number of row counts for the listview. Typically this contains
     * the size of the list you passing as input. */
    @Override
    public int getCount() {
        return exps.size();
    }

    /* Returns object representing data for each row. */
    @Override
    public Object getItem(int position) {
        return exps.get(position);
    }

    /* This returns the unique integer id that represents each row item. Let us return the integer
    * position value. */
    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder;

        if(view == null) {
            LayoutInflater li = sActivity.getLayoutInflater();
            view = li.inflate(R.layout.game_expsel_item, null);

            holder = new ViewHolder();
            holder.img = (ImageView)view.findViewById(R.id.exp_image);

            view.setTag(holder);
        }

        else {
            holder = (ViewHolder)view.getTag();
        }


        // Refresh View
        holder.img.setImageResource(exps.get(position).getImage());

        if(exps.get(position).isSelected()){
            view.setBackgroundResource(R.drawable.draw_rect_sel);
        }else{
            view.setBackgroundResource(R.drawable.draw_rect);
        }


        /* Quando un item viene cliccato */
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View item) {

                if (!item.isSelected() && !exps.get(position).isSelected()) {
                    item.setBackgroundResource(R.drawable.draw_rect_sel);
                    item.setSelected(true);
                    exps.get(position).setSelected(true);
                }

                else {
                    item.setBackgroundResource(R.drawable.draw_rect);
                    item.setSelected(false);
                    exps.get(position).setSelected(false);
                }
            }

        });

        return view;
    }

//  View Holder is a lightweight inner class that holds direct references to all inner views from a row.
//  You store it as a tag in the row’s view after inflating it. This way you’ll only have to use
//  findViewById() when you first create the layout.
    private static class ViewHolder{
        public ImageView img;
    }

}
