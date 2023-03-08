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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class InstrumentInfo extends AppCompatActivity {
    int position = -99;
    ArrayList<String> borrowers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_info);

        Bundle extras;

        ArrayList<Instrument> instruments = ListContainer.getInstruments();

        if(savedInstanceState == null)
        {
            extras  = getIntent().getExtras();
            position =extras.getInt("itemIndex", -99);
        }


        if (position==-99){
        //it doesn't exist so we need to make it
            TextView tvId = (TextView) findViewById(R.id.idNumber);
            tvId.setText(MainActivity.activeID);
        }

        //it does exist already

            // get single item from list
            Instrument currentInstrument = instruments.get(position);
            borrowers = currentInstrument.getBorrowers();

            // get the UI objects
            TextView tvId = (TextView) findViewById(R.id.idNumber);
            Integer i = instruments.get(position).getId();
            tvId.setText(i.toString());

            EditText etGroup = (EditText) findViewById(R.id.instGroup);
            etGroup.setText(instruments.get(position).getGroup());

            CheckBox checkStatus = (CheckBox) findViewById(R.id.checkedOut);
            checkStatus.setChecked(instruments.get(position).getStatus());

            CheckBox checkDamage = (CheckBox) findViewById(R.id.damaged);
            checkDamage.setChecked(instruments.get(position).getDamage());

            ListView lvBorrowers = (ListView) findViewById(R.id.borrowerHistory);
            ArrayAdapter<String> borrowerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, borrowers);
            lvBorrowers.setAdapter(borrowerAdapter);


        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etGroup = (EditText) findViewById(R.id.instGroup);
                String instGroup = etGroup.getText().toString();

                EditText etBorrower = (EditText) findViewById(R.id.borrowerEdit);
                String newBorrower = etBorrower.getText().toString();
                if(position != -99) {
                    instruments.get(position).setStatus(checkStatus.isChecked());
                    instruments.get(position).setDamaged(checkDamage.isChecked());
                    if (!newBorrower.equals(" ")) {
                        instruments.get(position).addBorrowerToTop(newBorrower);
                    }
                    instruments.get(position).setGroup(instGroup);
                }
                else{
                    Instrument newInstr = new Instrument(MainActivity.activeID,instGroup,checkStatus.isChecked(),checkDamage.isChecked());
                    instruments.add(newInstr);
                }

            }
        });
    }
}