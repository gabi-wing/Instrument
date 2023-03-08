package com.example.instrument;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class InstrumentListAdapter extends ArrayAdapter<Instrument>{

    public InstrumentListAdapter(@NonNull Context context, int resource, @NonNull List<Instrument> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convert, ViewGroup parent) {

        View currentView = convert;
        if (currentView == null)
            currentView = LayoutInflater.from(getContext()).inflate(R.layout.single_intrument_item, parent, false);

        Instrument instrument = (Instrument) getItem(position);

        TextView tvId = (TextView) currentView.findViewById(R.id.idText);
        Integer i = instrument.getId();
        tvId.setText(i.toString());

        TextView tvGroup = (TextView) currentView.findViewById(R.id.groupText);
        tvGroup.setText(instrument.getGroup());

        return currentView;
    }
}
