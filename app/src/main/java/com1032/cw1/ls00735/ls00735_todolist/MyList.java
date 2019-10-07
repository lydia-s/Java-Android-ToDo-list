package com1032.cw1.ls00735.ls00735_todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author savou
 * Created by savou on 28/02/2017.
 * Array adapter converts array items into string object and puts them into text view
 */
public class MyList extends ArrayAdapter<String>{
    private ArrayList<String> checkPoints;

    /**
     *
     * @param context
     * @param checkPoints
     */
    public MyList(Context context, ArrayList<String> checkPoints){
        super(context, R.layout.row_layout, checkPoints);
        this.checkPoints = checkPoints;

    }

    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     *
     */
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)
                this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.text);
        textView.setText(checkPoints.get(position));
        ImageView imageView = (ImageView) rowView.findViewById(R.id.item_image);
        return rowView;

    }




}
