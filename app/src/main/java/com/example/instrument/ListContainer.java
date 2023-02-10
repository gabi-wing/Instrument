package com.example.instrument;

import java.util.ArrayList;

public class ListContainer {
    private static ArrayList<Instrument> instruments;


    public static ArrayList<Instrument> getInstruments(){
        if (instruments==null)
            instruments = new ArrayList<>();


        return instruments;
    }

}
