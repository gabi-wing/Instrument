package com.example.instrument;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class InstrumentInfoAdapter extends ArrayAdapter<Instrument>{

    public InstrumentInfoAdapter(@NonNull Context context, int resource, @NonNull List<Instrument> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convert, ViewGroup parent) {

        View currentView = convert;
        if (currentView == null)
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.activity_instrument_info, parent, false);

        Instrument instrument = (Instrument) getItem(position);

        TextView tvId = (TextView) currentView.findViewById(R.id.idNumber);
        tvId.setText(instrument.getId());

        TextView tvGroup = (TextView) currentView.findViewById(R.id.instGroup);
        tvGroup.setText(instrument.getGroup());

        return currentView;
    }
}

