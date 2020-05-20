//package com.example.mymoviememoir.viewmodel;
//
//import android.app.Application;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.mymoviememoir.entity.Watchlist;
//import com.example.mymoviememoir.repository.WatchlistRepository;
//
//import java.util.List;
//
//public class WatchlistViewModel {
//    private WatchlistRepository watchlistRepository;
//    private MutableLiveData<List<Watchlist>> allWatchlist;
//    public WatchlistViewModel(){
//        allWatchlist = new MutableLiveData<>();
//    }
//
//    public void setWatchlist(List<Watchlist> watchlist){
//        allWatchlist.setValue(watchlist);
//    }
//
//    public LiveData<List<Watchlist>> getAllWatchlist(){
//        return watchlistRepository.getAllWatchlist();
//    }
//
//    public void initializeVars(Application application){
//        watchlistRepository = new WatchlistRepository(application);
//    }
//    public void insert(Watchlist watchlist){
//        watchlistRepository.insert(watchlist);
//    }
//
//    public void insertAll(Watchlist... watchlists){
//        watchlistRepository.insertAll(watchlists);
//    }
//    public void deleteAll(){
//        watchlistRepository.deteleAll();
//    }
//    public void delete(Watchlist watchlist){
//        watchlistRepository.detele(watchlist);
//    }
//}
