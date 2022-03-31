package com.example.qrcodeteam30.controllerclass.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.modelclass.Game;

import java.util.ArrayList;
import java.util.Date;

/**
 * Custom Array Adapter for the listview in ChooseGameActivity
 */
public class CustomListChooseGame extends ArrayAdapter<Game> {
    ArrayList<Game> arrayList;
    Context context;

    public CustomListChooseGame(Context context, ArrayList<Game> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        var view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_choosegame_content, parent, false);
        }

        var game = arrayList.get(position);

        TextView textViewName = view.findViewById(R.id.listview_chooseGame_textView_name);
        TextView textViewOwner = view.findViewById(R.id.listview_chooseGame_textView_owner);
        TextView textViewDate = view.findViewById(R.id.listview_chooseGame_textView_date);

        textViewName.setText(game.getGameName());
        textViewOwner.setText("@" + game.getOwnerUsername());
        Date date = new Date(Long.parseLong(game.getDate()));
        textViewDate.setText(date.toString());

        return view;
    }
}
