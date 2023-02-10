package com.example.instrument;

public class Instrument {
    String idNumber = "";
    String group = "";
    boolean out = false;
    boolean damaged = false;


    public Instrument (String id, String group){
        idNumber = id;
        this.group = group;
    }

    public Instrument (String id, String group, boolean out, boolean damaged){
        idNumber = id;
        this.group = group;
        this.out = out;
        this.damaged = damaged;
    }

    public String getId(){return idNumber;}
    public String getGroup(){return group;}
    public boolean getStatus(){return out;}
    public void setStatus(boolean out){this.out = out;}
    public boolean getDamage(){return damaged;}
    public void setDamaged(boolean damaged) {this.damaged = damaged;}
}


