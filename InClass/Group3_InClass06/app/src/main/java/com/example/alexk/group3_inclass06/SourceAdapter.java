package com.example.alexk.group3_inclass06;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by AlexK on 2/26/2018.
 */

public class SourceAdapter extends ArrayAdapter<Source> {

    public Context context;

    public SourceAdapter(@NonNull Context context, int resource, List<Source> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Source source = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.source_item_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.setTextView_title((TextView) convertView.findViewById(R.id.textView_title));
            viewHolder.setTextView_description((TextView) convertView.findViewById(R.id.textView_description));
            viewHolder.setTextView_date((TextView) convertView.findViewById(R.id.textView_date));
            viewHolder.setImageView((ImageView) convertView.findViewById(R.id.imageButton));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.getTextView_title().setText(source.getTitle());
        viewHolder.getTextView_description().setText(source.getDescription());
        viewHolder.getTextView_date().setText(source.getPublishedDate());

        Picasso.with(context).load(source.getImage()).into(viewHolder.getImageView());

        return convertView;
    }

    private static class ViewHolder {
        //String title, publishedDate, image, description;
        TextView textView_title;
        TextView textView_description;
        TextView textView_date;
        ImageView imageView;

        public TextView getTextView_title() {
            return textView_title;
        }

        public void setTextView_title(TextView textView_title) {
            this.textView_title = textView_title;
        }

        public TextView getTextView_description() {
            return textView_description;
        }

        public void setTextView_description(TextView textView_description) {
            this.textView_description = textView_description;
        }

        public TextView getTextView_date() {
            return textView_date;
        }

        public void setTextView_date(TextView textView_date) {
            this.textView_date = textView_date;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        }
    }

