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
import com.example.qrcodeteam30.modelclass.Comment;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Custom adapter for showing all the comment of a QR Code
 * Use with ViewAllCommentActivity
 */
public class CustomListViewAllComment extends ArrayAdapter<Comment> {
    private ArrayList<Comment> arrayList;
    private Context context;

    public CustomListViewAllComment(Context context, ArrayList<Comment> arrayList) {
        super(context, 0, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        var view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_viewallcomment_content, parent, false);
        }

        var comment = arrayList.get(position);
        TextView textViewAuthor = view.findViewById(R.id.textViewAuthor_viewallcomment_listview_content);
        TextView textViewDate = view.findViewById(R.id.textViewDate_viewallcomment_listview_content);
        TextView textViewContent = view.findViewById(R.id.textViewContent_viewallcomment_listview_content);
        textViewAuthor.setText(String.format(Locale.CANADA, "@%s", comment.getAuthor()));
        textViewDate.setText(comment.getDate());
        textViewContent.setText(comment.getContent());

        return view;
    }
}
