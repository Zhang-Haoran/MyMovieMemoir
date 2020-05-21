package com.example.mymoviememoir.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mymoviememoir.entity.Watchlist;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

    @Dao
    public interface WatchlistDAO {
        @Query("SELECT * FROM Watchlist")
        LiveData<List<Watchlist>> getAll();

        @Query("UPDATE watchlist SET movieName = :movieName,releaseDate = :releaseDate,addingDate = :addingDate where uid = :uid")
        void updateByID(int uid,String movieName,String releaseDate,String addingDate);

        @Query("SELECT * FROM Watchlist WHERE uid = :watchlistId LIMIT 1")
        Watchlist findByID(int watchlistId);

        @Insert
        void insertAll(Watchlist... watchlists);
        @Insert long insert(Watchlist watchlist);

        @Delete
        void delete(Watchlist watchlist);

        @Update(onConflict = REPLACE)
        void updateWatchlist(Watchlist... watchlists);
        @Query("DELETE FROM Watchlist")
        void deleteAll(); }
