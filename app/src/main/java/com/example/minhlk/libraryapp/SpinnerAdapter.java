package com.example.minhlk.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<Author> {
    // Your sent context
    private Context context;
    // Your custom values for the spinner (Author)
    private List<Author> authors;

    public SpinnerAdapter(Context context, int textViewResourceId,
                          List<Author>  authors) {
        super(context, textViewResourceId, authors);
        this.context = context;
        this.authors = authors;
    }

    @Override
    public int getCount(){
        return authors.size();
    }

    @Override
    public Author getItem(int position){
        return authors.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Authors array) and the current position
        // You can NOW reference each method you has created in your bean object (Author class)
        label.setText(authors.get(position).getName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(authors.get(position).getName());

        return label;
    }
}

