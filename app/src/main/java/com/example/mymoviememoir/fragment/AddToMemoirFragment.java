package com.example.mymoviememoir.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.R;

public class AddToMemoirFragment extends Fragment {
    private TextView textView;
    private String movieID;
    private String movieName;
    private String releaseDate;
    private String addingDatetime;
    private Bitmap bitmap;
    public AddToMemoirFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.addtomemoir_fragment,container,false);
        textView = view.findViewById(R.id.addtomemoirTextView);
        textView.setText("Add To Memoir Screen");

        ImageView movieImage = view.findViewById(R.id.movieImage);
        TextView movieNameTextView = view.findViewById(R.id.addAMemoirMovieName);
        TextView releaseDateTextView = view.findViewById(R.id.addaMemoirReleaseDate);

        Bundle bundle = getArguments();
        movieID = bundle.getString("movieID");
        movieName = bundle.getString("movieName");
        releaseDate = bundle.getString("releaseDate");
        bitmap = bundle.getParcelable("Image");

        movieNameTextView.setText(movieName);
        releaseDateTextView.setText(releaseDate);
        movieImage.setImageBitmap(bitmap);

        return view;    }
}
