package com.example.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;
    public CustomAdapter(Context context , ArrayList<Word> arraylist,int color)
    {
        super(context,0,arraylist);
        this.mColorResourceId=color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        ImageView imageView=(ImageView)listItemView.findViewById(R.id.image);
        TextView english =(TextView)listItemView.findViewById(R.id.english_text);
        TextView miwok =(TextView)listItemView.findViewById(R.id.miwok_text);
       Word currentWord = getItem(position);

       //this logic is to check that word has image or not.
        if (true==currentWord.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageView.setImageResource(currentWord.getImageResourceId());
            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            imageView.setVisibility(View.GONE);
        }
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);


        imageView.setImageResource(currentWord.getImageResourceId());
        english.setText(currentWord.getDefaultTranslation());
        miwok.setText(currentWord.getMiwoktranslation());
        return listItemView;
    }
}
