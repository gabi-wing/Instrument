package com.example.instrument;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static int activeID;
    InstrumentListAdapter adapter;
    ArrayList<Instrument> instruments;
    ArrayList<Instrument> filteredList = new ArrayList<>();

    CheckBox checkYesNeed;
    CheckBox checkNoNeed;
    CheckBox checkIn;
    CheckBox checkOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instruments = ListContainer.getInstruments();

        Gson gson = new Gson();
        String json = load("instruments");
        Type instrumentType = new TypeToken<ArrayList<Instrument>>(){}.getType();
        instruments = gson.fromJson(json, instrumentType);
        ListContainer.setInstruments(instruments);

        TextView tvScan = (TextView) findViewById(R.id.scan);
        tvScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scan = new Intent(getApplicationContext(), Scanner.class);
                startActivity(scan);
            }
        });


        Group filterGroup = findViewById(R.id.filterGroup);
        Button filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filterGroup.getVisibility()==View.GONE){
                    filterGroup.setVisibility(View.VISIBLE);
                }
                else{
                    filterGroup.setVisibility(View.GONE);
                }
            }
        });


        adapter = new InstrumentListAdapter(this, R.layout.single_intrument_item, instruments);
        ListView listView = (ListView) findViewById(R.id.instrumentList);
        listView.setAdapter(adapter);

         checkYesNeed = (CheckBox) findViewById(R.id.needs);
         checkNoNeed = (CheckBox) findViewById(R.id.none);
         checkIn = (CheckBox) findViewById(R.id.in);
         checkOut = (CheckBox) findViewById(R.id.out);


         checkYesNeed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    filteredList = SearchDamage(instruments, true);
                    adapter = new InstrumentListAdapter(MainActivity.this, R.layout.single_intrument_item, filteredList);
                    ListView listView = (ListView) findViewById(R.id.instrumentList);
                    listView.setAdapter(adapter);
                }
                else{
                    adapter = new InstrumentListAdapter(MainActivity.this, R.layout.single_intrument_item, instruments);
                    ListView listView = (ListView) findViewById(R.id.instrumentList);
                    listView.setAdapter(adapter);
                }
            }
        });


        checkNoNeed.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    filteredList = SearchDamage(instruments, false);
                    adapter = new InstrumentListAdapter(MainActivity.this, R.layout.single_intrument_item, filteredList);
                    ListView listView = (ListView) findViewById(R.id.instrumentList);
                    listView.setAdapter(adapter);
                }
                else{
                    adapter = new InstrumentListAdapter(MainActivity.this, R.layout.single_intrument_item, instruments);
                    ListView listView = (ListView) findViewById(R.id.instrumentList);
                    listView.setAdapter(adapter);
                }
            }
        });

        checkIn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    filteredList = SearchStatus(instruments, false);
                    adapter = new InstrumentListAdapter(MainActivity.this, R.layout.single_intrument_item, filteredList);
                    ListView listView = (ListView) findViewById(R.id.instrumentList);
                    listView.setAdapter(adapter);
                }
                else{
                    adapter = new InstrumentListAdapter(MainActivity.this, R.layout.single_intrument_item, instruments);
                    ListView listView = (ListView) findViewById(R.id.instrumentList);
                    listView.setAdapter(adapter);
                }
            }
        });


        checkOut.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(isChecked){
                    filteredList = SearchStatus(instruments, true);
                    adapter = new InstrumentListAdapter(MainActivity.this, R.layout.single_intrument_item, filteredList);
                    ListView listView = (ListView) findViewById(R.id.instrumentList);
                    listView.setAdapter(adapter);
                }
                else{
                    adapter = new InstrumentListAdapter(MainActivity.this, R.layout.single_intrument_item, instruments);
                    ListView listView = (ListView) findViewById(R.id.instrumentList);
                    listView.setAdapter(adapter);
                }
            }
        });


        //handler for clicking items in instrument list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent singleItem = new Intent(getApplicationContext(), InstrumentInfo.class);
                singleItem.putExtra("itemIndex",position);
                startActivity(singleItem);


            }
        });


    }

    public ArrayList SearchDamage(ArrayList<Instrument> inst, boolean yesOrNo){
        ArrayList<Instrument> temp = new ArrayList<>();
        for(int i = 0; i < inst.size(); i++){
            if(inst.get(i).getDamage() == yesOrNo){
                temp.add(inst.get(i));
            }
        }
        return temp;
    }

    public ArrayList SearchStatus(ArrayList<Instrument> inst, boolean inOrOut){
        ArrayList<Instrument> temp = new ArrayList<>();
        for(int i = 0; i < inst.size(); i++){
            if(inst.get(i).getStatus() == inOrOut){
                temp.add(inst.get(i));
            }
        }
        return temp;
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();

    }
    @Override
    protected void onStop(){
        //when it is going to save
        super.onStop();
        Gson gson = new Gson();
        String json = gson.toJson(instruments);
        save("instruments", json);

    }

    public void save(String k, String v){
        //save key value pairs to shared pref
        SharedPreferences sh = getSharedPreferences("Instrument", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        myEdit.putString(k,v);
        myEdit.apply();

    }

    public String load(String k){
        SharedPreferences sh = getSharedPreferences("Instrument", MODE_PRIVATE);
        return sh.getString(k,"");
    }

}