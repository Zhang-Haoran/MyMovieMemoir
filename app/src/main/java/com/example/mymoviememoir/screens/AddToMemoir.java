package com.example.mymoviememoir.screens;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.entity.Cinematable;
import com.example.mymoviememoir.entity.Memoirtable;
import com.example.mymoviememoir.entity.Usertable;
import com.example.mymoviememoir.serverConnection.Server;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddToMemoir extends Fragment {
    private TextView textView;
    private String movieID;
    private String movieName;
    private String releaseDate;
    private String addingDatetime;
    private Bitmap bitmap;
    private String spinnerResult;
    private String watchedDate;

    public AddToMemoir(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.addtomemoir_fragment,container,false);
        textView = view.findViewById(R.id.addtomemoirTextView);
        textView.setText("Add To Memoir Screen");
        final RatingBar ratingBar = view.findViewById(R.id.ratingBar2);
        ImageView movieImage = view.findViewById(R.id.movieImage);
        TextView movieNameTextView = view.findViewById(R.id.addAMemoirMovieName);
        TextView releaseDateTextView = view.findViewById(R.id.addaMemoirReleaseDate);
        final TextView commentTextview = view.findViewById(R.id.commentTextView);

        Bundle bundle = getArguments();
        movieID = bundle.getString("movieID");
        movieName = bundle.getString("movieName");
        releaseDate = bundle.getString("releaseDate");
        bitmap = bundle.getParcelable("Image");

        movieNameTextView.setText(movieName);
        releaseDateTextView.setText(releaseDate);
        movieImage.setImageBitmap(bitmap);


        Calendar calendar = Calendar.getInstance();// get a calendar using the current time zone and locale of the system. For example today is 10/05/2020, it will get 10,4, 2020
        final int calendarYear = calendar.get(Calendar.YEAR);
        final int calendarMonth = calendar.get(Calendar.MONTH);// month from calendar starts from 0 end in 11. it needs to + 1 to represent current month
        final int calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
        Button datetimePickerButton = view.findViewById(R.id.datetimePickerButton);
        final TextView watchedDateTextView = view.findViewById(R.id.watchedDate);
        //date time picker
        datetimePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Day of birth
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        watchedDate = dateFormatting(year,month,day);
                        watchedDateTextView.setText(watchedDate);
                    }
                },calendarYear,calendarMonth,calendarDay); //set current day, month, year into datepicker

                DatePicker dp = datePickerDialog.getDatePicker();//initialize datepicker to set max date, get the datetime that user picked
                dp.setMaxDate(System.currentTimeMillis()); //dob is no more than current date
                datePickerDialog.show();
            }

        });

       new findAllCinemaAsyncTask().execute();


        Button addCinema = view.findViewById(R.id.addNewCinema);
        addCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getActivity());
                final View promptsView = li.inflate(R.layout.addcinema,null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());
                alertDialogBuilder.setView(promptsView);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        EditText cName = promptsView.findViewById(R.id.addCinemaName);
                                        EditText cSuburb = promptsView.findViewById(R.id.addCinemaSuburb);
                                        String cinemaName = cName.getText().toString().trim();
                                        String cinemaSuburb = cSuburb.getText().toString().trim();
                                        Cinematable cinematable = new Cinematable(Home.userid+1000,cinemaName,cinemaSuburb,"");

                                            addCinemaAsyncTask addCinemaAsyncTask = new addCinemaAsyncTask();
                                            addCinemaAsyncTask.execute(cinematable);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();

            }
        });


        Button addMemoir = view.findViewById(R.id.AddMemoir);
        addMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ratingScore = String.valueOf(ratingBar.getRating());
                String[] watchDatetime =watchedDateTextView.getText().toString().split("T");


                   String watchDate = watchDatetime[0];


                   String watchTime = "T"+watchDatetime[1];



                String comment = commentTextview.getText().toString();
                String cinemaid = "1";
                int userid = Home.userid;
                Memoirtable memoirtable = new Memoirtable(Integer.parseInt(movieID)
                        ,movieName,releaseDate,
                        watchDate,
                        watchTime,
                        comment,
                        Integer.parseInt(ratingScore),
                        new Cinematable(cinemaid),
                        new Usertable(userid));



           }
        });



        return view;



    }

    private class findAllCinemaAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> cinemaList = new ArrayList<>();
            String allCinema = Server.findAllCinema();
            JsonElement jsonElement = new JsonParser().parse(allCinema);
            JsonArray jsonArray =jsonElement.getAsJsonArray();
            for (JsonElement j: jsonArray){
                JsonObject jsonObject = j.getAsJsonObject();
                String cinema = jsonObject.getAsJsonPrimitive("cinemaid").getAsString() + ")" + jsonObject.getAsJsonPrimitive("cinemaname").getAsString() + " " + jsonObject.getAsJsonPrimitive("suburb").getAsString();
                cinemaList.add(cinema);
            }
            return cinemaList;
        }
        @Override
        protected void onPostExecute(ArrayList<String> cinemas) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, cinemas);
            Spinner spinner = getView().findViewById(R.id.CinemaSpinner);
            spinner.setAdapter(arrayAdapter);


        }

    }



    private class addCinemaAsyncTask extends AsyncTask<Cinematable, Void,Void>{
        @Override
        protected Void doInBackground(Cinematable... cinematables) {
            Server.addCinema(cinematables[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            new findAllCinemaAsyncTask().execute();
        }
    }
    private class postMemoirAsyncTask extends AsyncTask<Memoirtable,Void,Void>{

        @Override
        protected Void doInBackground(Memoirtable... memoirtables) {
            Server.postMemoirtable(memoirtables[0]);
            return null;
        }
    }



    //formatting date
    public String dateFormatting(int year, int month, int day){
        String result ="";
        int currentmonth = month + 1; //month from datepicker is current month -1, therefore, we need to add 1 back
        String monthFormatting = String.valueOf(currentmonth);
        String dayFormatting = String.valueOf(day);
        if (monthFormatting.length() == 1){
            monthFormatting = "0" + monthFormatting;//append the month at the end of stringBuffer which is after 0
        }
        if (dayFormatting.length() == 1){
            dayFormatting = 0 + dayFormatting;
        }
        result = year + "-" + monthFormatting + '-' + dayFormatting + "T00:00:00+00:00";
        return result;
    }
}
