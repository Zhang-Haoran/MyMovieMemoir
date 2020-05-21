package com.example.mymoviememoir.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.entity.Watchlist;
import com.example.mymoviememoir.repository.WatchlistRepository;

import java.util.List;

public class WatchlistViewModel extends ViewModel {
    private WatchlistRepository watchlistRepository;
    private MutableLiveData<List<Watchlist>> allWatchlist;
    public WatchlistViewModel(){
        allWatchlist = new MutableLiveData<>();
    }

    public void setWatchlist(List<Watchlist> watchlist){
        allWatchlist.setValue(watchlist);
    }

    public LiveData<List<Watchlist>> getAllWatchlist(){
        return watchlistRepository.getAllWatchlist();
    }

    public void initializeVars(Application application){
        watchlistRepository = new WatchlistRepository(application);
    }
    public void insert(Watchlist watchlist){
        watchlistRepository.insert(watchlist);
    }

    public void insertAll(Watchlist... watchlists){
        watchlistRepository.insertAll(watchlists);
    }
    public void deleteAll(){
        watchlistRepository.deteleAll();
    }
    public void update(Watchlist...watchlists){
        watchlistRepository.updateWatchlists(watchlists);
    }
    public void updateByID(int uid,String movieName,String releaseDate,String addingDate){
        watchlistRepository.updateWatchlistByID(uid,movieName,releaseDate,addingDate);
    }
    public Watchlist findByID(int id){
        return watchlistRepository.findByID(id);
    }
}
