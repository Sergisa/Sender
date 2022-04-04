package ru.sergisa.sender.activity;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import ru.sergisa.sender.R;
import ru.sergisa.sender.models.SenderResponse.Type;

public class SpinnerCustomAdapter extends ArrayAdapter<Type> {
    Context context;
    Type[] types;

    SpinnerCustomAdapter(Context context, int resource, Type[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.types = objects;
    }

    @Override
    public int getCount() {
        return types.length;
        //return super.getCount();
    }

    @Override
    public Type getItem(int position) {
        return types[position];
        //return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
        //return super.getItemId(position);
    }


    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);

    }

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item

        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)


        // And finally return your dynamic (or custom) view for each spinner item

        */
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View row = inflater.inflate(R.layout.row, parent, false);

        //row.setBackgroundResource(android.R.drawable.alert_dark_frame);

        TextView tv = row.findViewById(R.id.type_name);
        tv.setTextColor(Color.BLACK);
        tv.setText(types[position].getLanguageName());

        return row;
    }

}
