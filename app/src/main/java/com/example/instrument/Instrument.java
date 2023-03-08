package com.example.instrument;

import java.io.Serializable;
import java.util.ArrayList;

public class Instrument implements Serializable {
    int idNumber = -1;
    String group = "";
    boolean out = false;
    boolean damaged = false;


    public Instrument (int id, String group){
        idNumber = id;
        this.group = group;
        borrowers = new ArrayList<String>();
    }

    public Instrument (int id, String group, boolean out, boolean damaged){
        idNumber = id;
        this.group = group;
        this.out = out;
        this.damaged = damaged;
        borrowers = new ArrayList<String>();
    }

    ArrayList<String> borrowers;

    public int getId(){return idNumber;}
    public String getGroup(){return group;}
    public void setGroup(String group){this.group = group;}
    public boolean getStatus(){return out;}
    public void setStatus(boolean out){this.out = out;}
    public boolean getDamage(){return damaged;}
    public void setDamaged(boolean damaged) {this.damaged = damaged;}
    public ArrayList<String> getBorrowers(){return borrowers;}
    public void addBorrower(String borrower){borrowers.add(borrower);}
    public void addBorrowerToTop(String borrower){borrowers.add(0,borrower);}
    public boolean removeBorrower(String b){return borrowers.remove(b);}
    public String removeBorrower(int i){return borrowers.remove(i);}


}


