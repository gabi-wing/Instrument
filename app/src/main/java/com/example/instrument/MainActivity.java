package com.example.instrument;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        ArrayList<Instrument> instruments = ListContainer.getInstruments();
        instruments.add(new Instrument("Fl1","flute",true,false));
        instruments.add(new Instrument("Fl2","flute"));
        instruments.add(new Instrument("Sx1","saxophone"));
        instruments.add(new Instrument("Cl1","clarinet"));
        instruments.add(new Instrument("Tu1","tuba"));
        instruments.add(new Instrument("Tr1","trombone"));


        InstrumentListAdapter adapter = new InstrumentListAdapter(this, R.layout.single_intrument_item, instruments);
        ListView listView = (ListView) findViewById(R.id.instrumentList);
        listView.setAdapter(adapter);

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

}