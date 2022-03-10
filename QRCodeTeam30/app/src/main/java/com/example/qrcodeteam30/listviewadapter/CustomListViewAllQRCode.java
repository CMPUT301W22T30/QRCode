package com.example.qrcodeteam30.listviewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qrcodeteam30.R;
import com.example.qrcodeteam30.modelclass.QRCode;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CustomListViewAllQRCode extends ArrayAdapter<QRCode> {
    private ArrayList<QRCode> arrayList;
    private Context context;

    public CustomListViewAllQRCode(Context context, ArrayList<QRCode> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        var view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_viewallqrcode_content, parent, false);
        }

        var qrCode = arrayList.get(position);
        TextView textView_content = view.findViewById(R.id.listview_viewallqrcode_textView_content);
        TextView textView_score = view.findViewById(R.id.listview_viewallqrcode_textView_score);
        textView_content.setText(qrCode.getDate());
        BigDecimal strippedVal = new BigDecimal(Double.toString(qrCode.getScore())).stripTrailingZeros();
        textView_score.setText(strippedVal.toPlainString());

        return view;
    }
}
