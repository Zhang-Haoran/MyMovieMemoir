package com.example.mymoviememoir.networkconnection;

import com.example.mymoviememoir.Signin;
import com.example.mymoviememoir.entity.Credentialstable;
import com.example.mymoviememoir.entity.Usertable;
import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestClient {
    private static final String BASE_URL = "http://192.168.1.103:8080/FIT5046Assignment1/webresources/";

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

    public static void postUsertable(Usertable usertable){
        Gson gson = new Gson();
        String usertableJson = gson.toJson(usertable);
        postMethod("fit5046assignment1.usertable/",usertableJson);

    }

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

}
