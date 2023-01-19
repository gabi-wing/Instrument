package com.example.instrument;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        ArrayList<Instrument> instruments = new ArrayList<>();
        instruments.add(new Instrument("Fl1","flute"));
        instruments.add(new Instrument("Fl2","flute"));
        instruments.add(new Instrument("Sx1","saxophone"));
        instruments.add(new Instrument("Cl1","clarinet"));
        instruments.add(new Instrument("Tu1","tuba"));
        instruments.add(new Instrument("Tr1","trombone"));

        InstrumentAdapter adapter = new InstrumentAdapter(this, R.layout.single_intrument_item, instruments);
        ListView listView = (ListView) findViewById(R.id.instrumentList);
        listView.setAdapter(adapter);
    }

}