package com.example.mymoviememoir.screens;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.serverConnection.Server;

public class MovieMemoir extends Fragment {
    private TextView textView;
    private TextView findResultTextView;


    public MovieMemoir(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.moviememoir_fragment,container,false);
        textView = view.findViewById(R.id.moviememoirTextView);
        findResultTextView = view.findViewById(R.id.findResultTextView);
        Button findAllMemoirButton = view.findViewById(R.id.findAllMemoirButton);
        textView.setText("Movie Memoir Screen");
        findAllMemoirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoirAsyncTask getAllMovieMemoir = new MemoirAsyncTask();
                getAllMovieMemoir.execute();
            }
        });
        return view;
    }

    private class MemoirAsyncTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            return Server.findAllMovieMemoir();
        }
        protected  void onPostExecute(String memoir){

            findResultTextView.setText(memoir);
        }
    }

}
