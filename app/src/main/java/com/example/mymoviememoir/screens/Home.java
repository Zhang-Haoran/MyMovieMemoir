package com.example.mymoviememoir.screens;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.serverConnection.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Home extends Fragment {
    private TextView textView;
    private String nameOfUser;
    private int userid;
    private List<HashMap<String,String>> movieList = new ArrayList<>();
    private ListView movieListView;

    public Home(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        textView = view.findViewById(R.id.homeTextView);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        nameOfUser = bundle.getString("nameOfUser");
        userid = bundle.getInt("userid");
        TextView dateTextView = view.findViewById(R.id.dateTextView);
        movieListView = view.findViewById(R.id.top5MovieListView);
        new topFiveMovieAsyncTask().execute();
    //welcome the user and show the current data time
        textView.setText("Welcome "+ nameOfUser);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        dateTextView.setText(simpleDateFormat.format(date));


        return view;
    }
    // retrieve the first top five movie based on assignment 1 task 4 d
    private class topFiveMovieAsyncTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return Server.findHighRatingMovieNameByUserid(userid);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    HashMap<String,String> map = new HashMap<>();
                    map.put("Movie Name",jsonObject.getString("Movie Name"));
                    map.put("Release Date",jsonObject.getString("Release Date"));
                    map.put("Rating Score",jsonObject.getString("Rating Score"));
                    movieList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String[] colHEAD = new String[]{"Movie Name","Release Date","Rating Score"};
            int[] dataCell = new int[]{R.id.movieNameTextView,R.id.releaseDateTextView,R.id.ratingScoreTextView};
            SimpleAdapter movieListAdapter = new SimpleAdapter(Home.this.getActivity(),movieList,R.layout.list_view,colHEAD,dataCell);
            movieListView.setAdapter(movieListAdapter);
        }
    }
}
