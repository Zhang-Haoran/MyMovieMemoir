package com.example.mymoviememoir.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mymoviememoir.API.TheMovieDBAPI;
import com.example.mymoviememoir.MainActivity;
import com.example.mymoviememoir.R;
import com.example.mymoviememoir.Signin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieSearchFragment extends Fragment {
    private TextView textView;
    private String searchInput;
    private List<HashMap<String,Object>> movieSearchList = new ArrayList<>();
    ListView movieListView;
    List<String> resultList;

    public MovieSearchFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.moviesearch_fragment,container,false);
        textView = view.findViewById(R.id.moviesearchTextView);
        textView.setText("Movie Search Screen");
        Button searchButton = view.findViewById(R.id.searchButton);
        final EditText searchInputField = view.findViewById(R.id.searchInputField);
        movieListView = view.findViewById(R.id.movieListView);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = searchInputField.getText().toString();
                if (searchInput.isEmpty()){
                    searchInputField.setError("search could not be empty");
                    Toast.makeText(getActivity(),"please check your input of search",Toast.LENGTH_SHORT).show();
                }
                new AsyncSearch().execute(searchInput);
            }
        });
        return view;    }
        //search move from API
        private class AsyncSearch extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... strings) {
                return TheMovieDBAPI.searchMovie(strings[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                new AsyncSearchResult().execute(result);
            }
        }

        //pass the search result into list view
        private class AsyncSearchResult extends AsyncTask<String,Void, SimpleAdapter>{

            @Override
            protected SimpleAdapter doInBackground(String... strings) {
                resultList = TheMovieDBAPI.getSnippet(strings[0]);
                for (int i = 0; i < resultList.size(); i+=4){
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("Movie Name",resultList.get(i));
                    map.put("Release Date",resultList.get(i+1));
                    String imgURL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/" + resultList.get(i+2);
                    map.put("movieID",resultList.get(i+3));
                    Bitmap bitmap;
                    try {
                        URL url = new URL(imgURL);
                        InputStream inputStream = url.openStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        inputStream.close();
                        map.put("Image",bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    movieSearchList.add(map);
                }
                String[] colHEAD = new String[]{"Image","Movie Name","Release Date","movieID"};
                int[] dataCell = new int[]{R.id.image,R.id.movieName,R.id.releaseYear,R.id.movieid};
                SimpleAdapter movieListAdapter = new SimpleAdapter(MovieSearchFragment.this.getActivity(),movieSearchList,R.layout.list_view_movie_search,colHEAD,dataCell);
                movieListAdapter.setViewBinder(new SimpleAdapter.ViewBinder(){

                    public boolean setViewValue(View view, Object data, String textRepresentation) {
                        if((view instanceof ImageView) & (data instanceof Bitmap) ) {
                            ImageView imageView = (ImageView) view;
                            Bitmap bitmap = (Bitmap) data;
                            imageView.setImageBitmap(bitmap);
                            return true;
                        }
                        return false;
                    }
                });
                return movieListAdapter;
            }

            @Override
            protected void onPostExecute(SimpleAdapter simpleAdapter) {
                super.onPostExecute(simpleAdapter);
                movieListView.setAdapter(simpleAdapter);
                movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String movieID = movieSearchList.get(position).get("movieID").toString();
                        String movieName = movieSearchList.get(position).get("Movie Name").toString();
                        Fragment fragment = new MovieViewFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("movieID",movieID);
                        bundle.putString("movieName",movieName);

                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
                    }
                });
            }
        }

}
