package com.example.mymoviememoir.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.API.TheMovieDBAPI;
import com.example.mymoviememoir.R;

import java.util.List;

public class MovieViewFragment extends Fragment {
    private TextView textView;
    private String movieID;
    private String movieName;
    TextView mvreleasedate;
    TextView mvgenre;
    TextView cast;
    TextView mvcountry;
    TextView director;
    TextView summary;
    TextView ratingScore;
    List<String> resultList;
    List<String> resultList2;
    List<String> resultList3;
    public MovieViewFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.movieview_fragment,container,false);
        textView = view.findViewById(R.id.movieviewTextView);
        textView.setText("This is Movie View Screen");
        TextView mvmoviename = view.findViewById(R.id.mvmoviename);
        mvreleasedate = view.findViewById(R.id.mvreleasedate);
        mvgenre = view.findViewById(R.id.mvgenre);
        cast = view.findViewById(R.id.cast);
        mvcountry = view.findViewById(R.id.mvcountry);
        director = view.findViewById(R.id.director);
        summary = view.findViewById(R.id.summary);
        ratingScore = view.findViewById(R.id.ratingScore);
        Bundle bundle = getArguments();
        movieID = bundle.getString("movieID");
        movieName = bundle.getString("movieName");
        mvmoviename.setText(movieName);
        new AsyncMovieDetail().execute(movieID);



        return view;    }
//get movie detail from the movieDB api which is movie detail api
        private class AsyncMovieDetail extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... strings) {
                return TheMovieDBAPI.getMovieDetail(strings[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                new AsnycGetDetail().execute(result);
            }
        }
//get the target data and pass them into view
        private class AsnycGetDetail extends AsyncTask<String, Void, Void> {

            @Override
            protected Void doInBackground(String... strings) {
                resultList = TheMovieDBAPI.getDetails(strings[0]);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                mvcountry.setText(resultList.get(0));
                mvgenre.setText(resultList.get(1));
                mvreleasedate.setText(resultList.get(2));
                summary.setText(resultList.get(3));
                ratingScore.setText(resultList.get(4));
                new AsyncGetCredit().execute(movieID);
            }
        }
//cast and director are in the different api section which is credit api
        private class AsyncGetCredit extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... strings) {
                return TheMovieDBAPI.getCredit(strings[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                new AsyncGetCastAndDirector().execute(s);
            }
        }
//get the target data and pass them into view
        private class AsyncGetCastAndDirector extends AsyncTask<String,Void,Void>{

            @Override
            protected Void doInBackground(String... strings) {
                resultList2 = TheMovieDBAPI.getCast(strings[0]);
                resultList3 = TheMovieDBAPI.getDirector(strings[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                cast.setText(resultList2.get(0));
                director.setText(resultList3.get(0));

            }
        }
}
