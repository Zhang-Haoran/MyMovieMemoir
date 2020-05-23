package com.example.mymoviememoir.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TheMovieDBAPI {
    private static final String apiKey = "78324a2485620d39b0d6d391ac0573e6";
//connect to the search movie api
    public static String searchMovie(String searchInput){
        searchInput = searchInput.trim().replace(" ","%20");

        URL url = null;
        HttpURLConnection httpURLConnection = null;
        String result = "";
        try {
            url = new URL("https://api.themoviedb.org/3/search/movie?api_key="+apiKey+"&language=en-US&query="+searchInput+"&page=1&include_adult=false");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(httpURLConnection.getInputStream());
            while (inStream.hasNextLine()) {
                result += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return result;
    }
//get the data for searched movie
    public static List<String> getSnippet(String result){
        List<String> snippet = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("results"));
                for (int i = 0; i< jsonArray.length();i++){
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    snippet.add(object.getString("title"));
                    try {
                        snippet.add(object.getString("release_date"));
                    } catch (JSONException e) {
                        snippet.add("not release");
                    }
                    snippet.add(object.getString("poster_path"));
                    snippet.add(object.getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return snippet;
    }
//connect the movie detail api
    public static String getMovieDetail(String movieID){
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        String result = "";
        try {
            url = new URL("https://api.themoviedb.org/3/movie/"+movieID+"?api_key=78324a2485620d39b0d6d391ac0573e6&language=en-US");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(httpURLConnection.getInputStream());
            while (inStream.hasNextLine()) {
                result += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return result;
    }
//get movie detail information as required
    public static List<String> getDetails(String result){
        List<String> details = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("production_companies"));
            JSONObject object = (JSONObject) jsonArray.get(0);
            details.add(object.getString("origin_country"));
            JSONArray jsonArray2 = new JSONArray(jsonObject.getString("genres"));
            JSONObject objec2 = (JSONObject) jsonArray2.get(0);
            details.add(objec2.getString("name"));
            details.add(jsonObject.getString("release_date"));
            details.add(jsonObject.getString("overview"));
            details.add(jsonObject.getString("vote_average"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;
    }
//get credit data which store cast and director
    public static String getCredit(String movieID){
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        String result = "";
        try {
            url = new URL("https://api.themoviedb.org/3/movie/"+ movieID + "/credits?api_key=78324a2485620d39b0d6d391ac0573e6");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            Scanner inStream = new Scanner(httpURLConnection.getInputStream());
            while (inStream.hasNextLine()) {
                result += inStream.nextLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        return result;
    }

//get cast from the Movie DB api in credit
    public static List<String> getCast(String result){
        List<String> details = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("cast"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                details.add(object.getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;
    }
//get direct name from the Movie DB api in credit
    public static List<String> getDirector(String result){
        List<String> details = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("crew"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                if (object.has("job")) {
                    if (object.getString("job").equals("Director")) {
                        details.add(object.getString("name"));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;
    }
}
