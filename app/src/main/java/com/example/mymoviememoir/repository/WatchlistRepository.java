package com.example.mymoviememoir.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mymoviememoir.DAO.WatchlistDAO;
import com.example.mymoviememoir.database.WatchlistDatabase;
import com.example.mymoviememoir.entity.Watchlist;

import java.util.List;

public class WatchlistRepository {
    private WatchlistDAO dao;
    private LiveData<List<Watchlist>> allWatchlist;
    private Watchlist watchlist;

    public WatchlistRepository(Application application){
        WatchlistDatabase db = WatchlistDatabase.getInstance(application);
        dao = db.watchlistDAO();
    }

    public LiveData<List<Watchlist>> getAllWatchlist(){
        allWatchlist = dao.getAll();
        return allWatchlist;
    }

    public void insert(final Watchlist watchlist){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(watchlist);
            }
        });
    }

    public void deteleAll(){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

    public void detele(final Watchlist watchlist){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(watchlist);
            }
        });
    }

    public void insertAll(final Watchlist... watchlists){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(watchlists);
            }
        });
    }

    public void updateWatchlists(final Watchlist... watchlists){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateWatchlist(watchlists);
            }
        });
    }

    public Watchlist findByID(final int watchlistID){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Watchlist runWatchlist = dao.findByID(watchlistID);
                setWatchlist(runWatchlist);
            }
        });
        return watchlist;
    }

    private void setWatchlist(Watchlist Watchlist) {
        this.watchlist = watchlist;
    }
}
