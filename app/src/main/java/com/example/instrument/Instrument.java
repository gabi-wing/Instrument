package com.example.instrument;

public class Instrument {
    String idNumber = "";
    String group = "";

    public Instrument (String id, String group){
        idNumber = id;
        this.group = group;
    }

    public String getId(){return idNumber;}
    public String getGroup(){return group;}
}


