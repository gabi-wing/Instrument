package com.example.instrument;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class InstrumentInfo extends AppCompatActivity {
    int position = -99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_info);

        Bundle extras;


        if(savedInstanceState == null)
        {
            extras  = getIntent().getExtras();
            position =extras.getInt("itemIndex", -99);
        }

        /*
        if (position==-99){
        // bad
        }

         */

        ArrayList<Instrument> instruments = ListContainer.getInstruments();

        // get single item from list
        Instrument currentInstrument = instruments.get(position);
        ArrayList<String> borrowers = currentInstrument.getBorrowers();

        // get the UI objects
        TextView tvId = (TextView) findViewById(R.id.idNumber);
        tvId.setText(instruments.get(position).getId());

        TextView tvGroup = (TextView) findViewById(R.id.instGroup);
        tvGroup.setText(instruments.get(position).getGroup());

        CheckBox checkStatus = (CheckBox) findViewById(R.id.checkedOut);
        checkStatus.setChecked(instruments.get(position).getStatus());

        CheckBox checkDamage = (CheckBox) findViewById(R.id.damaged);
        checkDamage.setChecked(instruments.get(position).getDamage());

        ListView lvBorrowers = (ListView) findViewById(R.id.borrowerHistory);
        ArrayAdapter<String> borrowerAdapter = new ArrayAdapter<String>(this,R.layout.single_borrower_item, borrowers);
        lvBorrowers.setAdapter(borrowerAdapter);


        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instruments.get(position).setStatus(checkStatus.isChecked());
                instruments.get(position).setDamaged(checkDamage.isChecked());
            }
        });
    }
}