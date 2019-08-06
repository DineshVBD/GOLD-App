package com.example.vbd.gold_feelyounger;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import com.example.vbd.gold_feelyounger.R;


public class CustomPlacesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> placeName;
    private ArrayList<String> ratingText;
    private ArrayList<String> openNowText;

    public CustomPlacesAdapter(Context context, ArrayList<String> placeName, ArrayList<String> ratingText, ArrayList<String> openNowText) {
        this.context = context;
        this.placeName = placeName;
        this.ratingText = ratingText;
        this.openNowText = openNowText;

      //  Log.d("data",placeName.get(0));
    }

    @Override
    public int getCount() {
        return placeName.size();
    }

    @Override
    public Object getItem(int i) {
        return placeName.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 3232;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null) view = view.inflate(context, R.layout.list_hospitals1, null);

        TextView placeTextView = (TextView) view.findViewById(R.id.placeNameTextView);
        TextView ratingTextView = (TextView) view.findViewById(R.id.ratingTextView);
        TextView openNowTextView = (TextView) view.findViewById(R.id.openingTime);

        placeTextView.setText(placeName.get(i));
        ratingTextView.setText(ratingText.get(i));
        openNowTextView.setText("Open now: " + openNowText.get(i));

        return view;
    }
}