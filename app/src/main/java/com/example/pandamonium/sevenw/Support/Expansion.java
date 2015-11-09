package com.example.pandamonium.sevenw.Support;

/**
 * Created by Pandamonium on 19/09/2015.
 */
public class Expansion {

    private boolean selected;
    private String name;
    private int image;

    public Expansion (boolean selected, String name, int image){
        this.selected = selected;
        this.name = name;
        this.image = image;
    }

    public boolean isSelected(){
        return selected;
    }

    public String getName(){
        return name;
    }

    public int getImage(){
        return image;
    }

    public void setSelected(boolean selected){
        this.selected=selected;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setImage(int image){
        this.image=image;
    }
}