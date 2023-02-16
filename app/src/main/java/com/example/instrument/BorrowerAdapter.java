package com.example.instrument;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class BorrowerAdapter extends ArrayAdapter<Instrument> {

    public BorrowerAdapter(@NonNull Context context, int resource, @NonNull List<Instrument> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convert, ViewGroup parent) {

        View currentView = convert;
        if (currentView == null)
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.single_borrower_item, parent, false);

        Instrument instrument = (Instrument) getItem(position);

        TextView tvName = (TextView) currentView.findViewById(R.id.nameText);



        return currentView;

    }

}