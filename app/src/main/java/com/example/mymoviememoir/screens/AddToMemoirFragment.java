package com.example.mymoviememoir.screens;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.R;

import java.util.ArrayList;
import java.util.List;

public class AddToMemoirFragment extends Fragment {
    private TextView textView;
    private String movieID;
    private String movieName;
    private String releaseDate;
    private String addingDatetime;
    private Bitmap bitmap;
    private String spinnerResult;

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

        TextView watchedDateTextView = view.findViewById(R.id.watchedDate);
        Spinner CinemaSpinner = view.findViewById(R.id.CinemaSpinner);
        //spinner
        List<String> cinemalist = new ArrayList<>();
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item,cinemalist);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        CinemaSpinner.setAdapter(spinnerAdapter);
        CinemaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerResult = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;    }
}
