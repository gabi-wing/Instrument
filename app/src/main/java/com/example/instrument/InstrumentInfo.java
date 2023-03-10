package com.example.instrument;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.DialogInterface;
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
    int id;
    boolean bExists;

    ArrayList<String> borrowers;
    DialogInterface.OnClickListener dialogClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument_info);

        Bundle extras;

        ArrayList<Instrument> instruments = ListContainer.getInstruments();

        if(savedInstanceState == null)
        {
            extras  = getIntent().getExtras();
            bExists = extras.getBoolean("exists", false);
            id = extras.getInt("ID", 0);
        }


        // FIX2
        // set position in list

        if (!bExists){
        //it doesn't exist so we need to make it
            TextView tvId = (TextView) findViewById(R.id.idNumber);
            tvId.setText(Integer.toString(id));

            instruments.add(new Instrument(id,""));
            position = instruments.size()-1;
        }
        else
        {
            position = findPosFromID(id);
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

        Button deleteButton = (Button) findViewById(R.id.delete);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            // on below line we are setting a click listener
                            // for our positive button
                            case DialogInterface.BUTTON_POSITIVE:
                                // on below line we are displaying a toast message.
                                instruments.remove(position);
                                finish();
                                return;
                            // on below line we are setting click listener
                            // for our negative button.
                            case DialogInterface.BUTTON_NEGATIVE:
                                return;

                        }
                    }
                };
                // on below line we are creating a builder variable for our alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                // on below line we are setting message for our dialog box.
                builder.setMessage("Are you sure you want to delete this instrument?")
                        // on below line we are setting positive button
                        // and setting text to it.
                        .setPositiveButton("Yes", dialogClickListener)
                        // on below line we are setting negative button
                        // and setting text to it.
                        .setNegativeButton("No", dialogClickListener)
                        // on below line we are calling
                        // show to display our dialog.
                        .show();

            }
        });

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
                finish();
            }
        });
    }

    private int findPosFromID(int id) {
        ArrayList<Instrument> instruments = ListContainer.getInstruments();

        for(int i = 0; i < instruments.size(); i++){
            if(instruments.get(i).getId() == id)
                return i;
        }
        return -99;
    }
}