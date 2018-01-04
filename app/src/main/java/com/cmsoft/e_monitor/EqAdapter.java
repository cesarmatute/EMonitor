package com.cmsoft.e_monitor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cesar on 03/01/18.
 */

public class EqAdapter extends ArrayAdapter<Earthquake> {
    private ArrayList<Earthquake> eqList;
    private Context context;
    private int layoutId;

    public EqAdapter(@NonNull Context context, int resource, @NonNull List<Earthquake> earthquakes) {
        super(context, resource, earthquakes);

        this.context = context;
        this.layoutId = resource;
        eqList = new ArrayList<>(earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = inflater.inflate(layoutId, null);

        TextView magnitudeTextView = (TextView) rootView.findViewById(R.id.eq_list_item_magnitude);
        TextView placeTextView = (TextView) rootView.findViewById(R.id.eq_list_item_place);

        Earthquake earthquake = eqList.get(position);

        magnitudeTextView.setText(earthquake.getMagnitude());
        placeTextView.setText(earthquake.getPlace());

        return rootView;
    }
}
