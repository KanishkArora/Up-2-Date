package com.example.android.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Health> {
    Context context;
    private static final String NAME = " null ";
    String replace = "Unknown";

    public Adapter (Activity context, ArrayList<Health> b)
    {
        super(context,0,b);
        this.context=context;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_container, parent, false);
        }
        Health C= getItem(position);

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.textView);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(C.Getname());

        TextView nameTextView1=(TextView) listItemView.findViewById((R.id.textView2));

        nameTextView1 .setText(C.GetTitle());

        TextView nameTextView2=(TextView) listItemView.findViewById((R.id.textView3));

        nameTextView2 .setText(C.GetDate());

        ImageView imageView= (ImageView)listItemView.findViewById(R.id.image);
        try
        {
            URL imageUrl= new URL(C.GetImage());
            Glide.with(context).load(imageUrl).into(imageView);


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        return listItemView;

    }
}

