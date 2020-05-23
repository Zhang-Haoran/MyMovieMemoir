package com.example.mymoviememoir.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.database.WatchlistDatabase;
import com.example.mymoviememoir.entity.Watchlist;
import com.example.mymoviememoir.viewmodel.WatchlistViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WatchlistFragment extends Fragment {
    private TextView textView;

    WatchlistDatabase db;
    EditText editText;
    TextView textView_insert;
    TextView textView_read;
    TextView textView_delete;
    TextView textView_update;
    WatchlistViewModel watchlistViewModel;
    public WatchlistFragment(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.watchlist_fragment,container,false);
        textView = view.findViewById(R.id.watchlistTextView);
        textView.setText("Watchlist Screen");

        Button addButton = view.findViewById(R.id.addButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);
        Button updateButton = view.findViewById(R.id.updateButton);
        editText = view.findViewById(R.id.editText);
        textView_insert = view.findViewById(R.id.textView_add);
        textView_read = view.findViewById(R.id.textView_read);
        textView_delete = view.findViewById(R.id.textView_delete);
        textView_update = view.findViewById(R.id.textView_update);
        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        watchlistViewModel.initializeVars(getActivity().getApplication());
        watchlistViewModel.getAllWatchlist().observe(this, new Observer<List<Watchlist>>() {
            @Override
            public void onChanged(List<Watchlist> watchlists) {
                String allWatchlists = "";
                for (Watchlist temp: watchlists){
                    String watchlistString = (temp.getUid()+" "+temp.getMovieName()+ " " + temp.getReleaseDate()+" "+temp.getAddingDate());
                    allWatchlists = allWatchlists + System.getProperty("line.separator") + watchlistString;
                }
                textView_read.setText("All data: "+allWatchlists);
            }
        });
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!editText.getText().toString().isEmpty()){
                        String[] details = editText.getText().toString().split(" ");
                        if (details.length == 4){
                            Watchlist watchlist = new Watchlist(Integer.parseInt(details[0]),details[1],details[2],details[3]);
                            watchlistViewModel.insert(watchlist);
                            textView_insert.setText("Added Record: "+ Arrays.toString(details));
                        }
                    }
                }
            });
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    watchlistViewModel.deleteAll();
                    textView_delete.setText("All data was deleted");
                }
            });

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] details = editText.getText().toString().split(" ");
                    if (details.length == 4){
                        watchlistViewModel.updateByID(new Integer(details[0]).intValue(),details[1],details[2],details[3]);
                    }
                }
            });

        return view;    }
}
