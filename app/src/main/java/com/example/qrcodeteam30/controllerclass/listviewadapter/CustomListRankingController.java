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
import com.example.qrcodeteam30.controllerclass.CalculateScoreController;
import com.example.qrcodeteam30.modelclass.UserInformation;
import com.example.qrcodeteam30.modelclass.UserScoreGameSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Custom adapter for showing all the ranking of the players based on total score
 * Use with RankingActivity
 */
public class CustomListRankingController extends ArrayAdapter<UserScoreGameSession> {

    private ArrayList<UserScoreGameSession> arrayList;
    private Context context;

    public CustomListRankingController(Context context, ArrayList<UserScoreGameSession> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        var view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_ranking_content, parent, false);
        }

        var userScoreGameSession = arrayList.get(position);
        TextView textViewName = view.findViewById(R.id.listview_ranking_content_textView_name);
        TextView textViewUserName = view.findViewById(R.id.listview_ranking_content_textView_username);
        TextView textViewScore = view.findViewById(R.id.listview_ranking_content_textView_score);
        textViewName.setText(String.format(Locale.CANADA, "%d. %s %s",
                position + 1, userScoreGameSession.getFirstName(), userScoreGameSession.getLastName()));
        textViewUserName.setText(String.format(Locale.CANADA, "@%s", userScoreGameSession.getUsername()));

        //BigDecimal strippedVal = new BigDecimal(Double.toString(userInformation.getScore())).stripTrailingZeros();
        BigDecimal strippedVal = new BigDecimal(Double.toString(userScoreGameSession.getScore())).stripTrailingZeros();
        textViewScore.setText(strippedVal.toPlainString());

        return view;
    }
}
