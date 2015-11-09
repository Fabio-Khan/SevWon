package com.example.pandamonium.sevenw.Support;

/**
 * Created by Pandamonium on 23/09/2015.
 */
public class Player {

    private boolean selected;
    private String name;
    private int image;

    private int team;

    /* Punteggi */
    private int guerra;
    private int monete;
    private int meraviglia;
    private int civilta;
    private int mercato;
    private int scienza;
    private int gilde;
    private int nero;
    private int leaders;
    private int sailors;

    private int TOT;
    private int Team_TOT;
    private int standing;

    /* usati solo per le standings */
    private int date;
    private int num;
    private int giocs;

     public Player(boolean selected, String name, int image){
        this.selected = selected;
        this.name = name;
        this.image = image;
        this.team = 0;

        this.guerra = 0;
        this.monete = 0;
        this.meraviglia = 0;
        this.civilta = 0;
        this.mercato = 0;
        this.scienza = 0;
        this.gilde = 0;
        this.nero = 0;
        this.leaders = 0;
        this.sailors = 0;

        this.TOT = 0;
        this.Team_TOT = 0;
        this.standing = 0;

        this.date = 0;
        this.num = 0;
        this.giocs = 0;
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
    public int getTeam () { return team; }

    public void setSelected(boolean selected){
        this.selected=selected;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setImage(int image){
        this.image=image;
    }
    public void setTeam(int team){
        this.team=team;
    }


    public int getGuerra() {
        return this.guerra;
    }
    public int getMonete() {
        return this.monete;
    }
    public int getMeraviglia() {
        return this.meraviglia;
    }
    public int getCivilta() {
        return this.civilta;
    }
    public int getMercato() {
        return this.mercato;
    }
    public int getScienza() {
        return this.scienza;
    }
    public int getGilde() {
        return this.gilde;
    }
    public int getNero() {
        return this.nero;
    }
    public int getLeaders() {
        return this.leaders;
    }
    public int getSailors() {
        return this.sailors;
    }

    public int getDate() {
        return this.date;
    }
    public int getNum() {
        return this.num;
    }



    public void setGuerra(int guerra) {
        this.guerra = guerra;
    }
    public void setMonete(int monete) {
        this.monete = monete;
    }
    public void setMeraviglia(int meraviglia) {
        this.meraviglia = meraviglia;
    }
    public void setCivilta(int civilta) {
        this.civilta = civilta;
    }
    public void setMercato(int mercato) {
        this.mercato = mercato;
    }
    public void setScienza(int scienza) {
        this.scienza = scienza;
    }
    public void setGilde(int gilde) {
        this.gilde = gilde;
    }
    public void setNero(int nero) {
        this.nero = nero;
    }
    public void setLeaders(int leaders) {
        this.leaders = leaders;
    }
    public void setSailors(int sailors) {
        this.sailors = sailors;
    }

    public int getStanding(){
        return this.standing;
    }

    public void setStanding(int standing){
        this.standing=standing;
    }

    public int getTOT(){
        return this.TOT;
//        return guerra+meraviglia+civilta+mercato+scienza+gilde+nero+leaders+sailors;
    }

    /* Debug */
    public void setTOT(int tot){
        this.TOT=tot;
    }

    public int getTeam_TOT(){ return this.Team_TOT; }

    public void setTeam_TOT(int teamtot){ this.Team_TOT = teamtot; }


    public void setDate(int date) {
        this.date = date;
    }
    public void setNum(int num) {
        this.num = num;
    }

    public int getGiocs() {
        return giocs;
    }

    public void setGiocs(int giocs) {
        this.giocs = giocs;
    }

}
