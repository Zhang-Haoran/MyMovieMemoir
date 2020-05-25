package com.example.mymoviememoir.networkconnection;

import com.example.mymoviememoir.entity.Credentialstable;
import com.example.mymoviememoir.entity.Usertable;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static final String BASE_URL = "http://192.168.1.103:8080/FIT5046Assignment1/webresources/";
    private static final String apiKey = "78324a2485620d39b0d6d391ac0573e6";
    private static String getMethod(String methodPath, String parameterInput){
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        String result = "";
        try {
            url = new URL(BASE_URL + methodPath + parameterInput);
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

    private static void postMethod(String methodPath, String jsonToPost){
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        try{
            url = new URL(BASE_URL + methodPath);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setFixedLengthStreamingMode(jsonToPost.getBytes().length);
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            PrintWriter output = new PrintWriter(httpURLConnection.getOutputStream());
            output.print(jsonToPost);
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            httpURLConnection.disconnect();//if not, can not go into onPost
        }

    }


    public static String findAllMovieMemoir() {
        return getMethod("fit5046assignment1.memoirtable/","");
    }
//sign in page method
    public static String findByUsernameAndPassword(String username, String password){
        return getMethod("fit5046assignment1.credentialstable/findByUsernameAndPassword/",username + "/" + password);
    }
//sign up page method
    public static String findAllUsername(){
        return getMethod("fit5046assignment1.credentialstable/findByAllUsername/","");
    }
//post data into user table
    public static void postUsertable(Usertable usertable){
        Gson gson = new Gson();
        String usertableJson = gson.toJson(usertable);
        postMethod("fit5046assignment1.usertable/",usertableJson);

    }
//post data into credentials table
    public static void postcredentialstable(Credentialstable credentialstable){
        Gson gson = new Gson();
        String credentialstableJson = gson.toJson(credentialstable);
        postMethod("fit5046assignment1.credentialstable/",credentialstableJson);

    }
//home page method
    public static String findHighRatingMovieNameByUserid(int userid){
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        String result = "";
        try {
            url = new URL(BASE_URL + "fit5046assignment1.memoirtable/findHighRatingMovieNameByUserid/" + userid);
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
